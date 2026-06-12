package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.PagoDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final List<String> METODOS_VALIDOS = Arrays.asList("efectivo", "tarjeta", "transferencia");

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public PagoDTO registrarPago(PagoDTO dto) {
        // Validaciones
        validarSocioExiste(dto.getIdSocio());
        validarSocioActivo(dto.getIdSocio());
        validarMetodoPago(dto.getMetodoPago());
        validarPersonalExiste(dto.getIdPersonalRegistro());

        // Obtener suscripción actual del socio
        String sqlSuscripcion = "SELECT id_tipo_suscripcion_actual, fecha_vencimiento FROM Socio WHERE id = ?";
        var row = jdbcTemplate.queryForMap(sqlSuscripcion, dto.getIdSocio());
        Integer idTipoActual = (Integer) row.get("id_tipo_suscripcion_actual");
        LocalDate fechaVencActual = ((java.sql.Date) row.get("fecha_vencimiento")).toLocalDate();

        Integer idTipoNuevo = dto.getIdTipoSuscripcion() != null ? dto.getIdTipoSuscripcion() : idTipoActual;
        validarMembresiaExisteYActiva(idTipoNuevo);

        // Obtener duración en días
        String sqlDuracion = "SELECT duracion_dias, precio FROM TipoSuscripcion WHERE id = ?";
        var tipoData = jdbcTemplate.queryForMap(sqlDuracion, idTipoNuevo);
        int duracionDias = (int) tipoData.get("duracion_dias");
        BigDecimal precio = (BigDecimal) tipoData.get("precio");

        validarMontoPago(idTipoNuevo, dto.getMonto().doubleValue());

        LocalDate nuevaVenc = fechaVencActual.isBefore(LocalDate.now()) ?
                LocalDate.now().plusDays(duracionDias) :
                fechaVencActual.plusDays(duracionDias);

        // Insertar pago
        String sqlPago = "INSERT INTO Pago (id_socio, monto, fecha_pago, nueva_fecha_vencimiento, metodo_pago) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlPago, dto.getIdSocio(), dto.getMonto(), LocalDate.now(), nuevaVenc, dto.getMetodoPago());

        // Actualizar socio
        String sqlUpdateSocio = "UPDATE Socio SET fecha_vencimiento = ?, estado = 'activo', id_tipo_suscripcion_actual = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdateSocio, nuevaVenc, idTipoNuevo, dto.getIdSocio());

        dto.setNuevaFechaVencimiento(nuevaVenc);
        dto.setMensaje("Pago registrado exitosamente");
        return dto;
    }

    private void validarSocioExiste(int idSocio) {
        String sql = "SELECT COUNT(1) FROM Socio WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idSocio);
        if (count == 0) throw new RuntimeException("Socio no existe");
    }

    private void validarSocioActivo(int idSocio) {
        String sql = "SELECT estado FROM Socio WHERE id = ?";
        String estado = jdbcTemplate.queryForObject(sql, String.class, idSocio);
        if (!"activo".equals(estado)) throw new RuntimeException("Socio no está activo");
    }

    public void validarMetodoPago(String metodo) {
        if (metodo == null || !METODOS_VALIDOS.contains(metodo.toLowerCase()))
            throw new RuntimeException("Método de pago inválido");
    }

    public void validarPersonalExiste(Integer id) {
        // Opcional
    }

    private void validarMembresiaExisteYActiva(int idTipo) {
        String sql = "SELECT COUNT(1) FROM TipoSuscripcion WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idTipo);
        if (count == 0) throw new RuntimeException("Tipo de suscripción no existe");
    }

    public void validarMontoPago(int idTipoSuscripcion, double monto) {
        String sql = "SELECT precio FROM TipoSuscripcion WHERE id = ?";
        double precio = jdbcTemplate.queryForObject(sql, Double.class, idTipoSuscripcion);
        if (Math.abs(precio - monto) > 0.01) {
            throw new RuntimeException("Monto incorrecto. El precio es " + precio);
        }
    }

    // Métodos adicionales para los endpoints de validación
    public void validarSuscripcionExisteYActiva(int idSuscripcion) {
        // Como no hay tabla Suscripcion en el modelo original, usamos TipoSuscripcion
        validarMembresiaExisteYActiva(idSuscripcion);
    }
}