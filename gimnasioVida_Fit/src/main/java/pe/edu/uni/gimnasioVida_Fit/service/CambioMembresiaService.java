package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.CambioMembresiaDTO;

import java.time.LocalDate;

@Service
public class CambioMembresiaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public CambioMembresiaDTO cambiarMembresia(CambioMembresiaDTO dto) {
        validarSocioExiste(dto.getIdSocio());
        validarSocioActivo(dto.getIdSocio());
        validarNuevaMembresiaExiste(dto.getIdNuevaMembresia());
        validarNoEsLaMismaMembresia(dto);

        // Obtener membresía actual
        String sqlActual = "SELECT id_tipo_suscripcion_actual, fecha_vencimiento FROM Socio WHERE id = ?";
        var actualRow = jdbcTemplate.queryForMap(sqlActual, dto.getIdSocio());
        int idActual = (int) actualRow.get("id_tipo_suscripcion_actual");
        LocalDate vencActual = ((java.sql.Date) actualRow.get("fecha_vencimiento")).toLocalDate();

        String sqlMembresia = "SELECT nombre, precio, duracion_dias FROM TipoSuscripcion WHERE id = ?";
        var actualMemb = jdbcTemplate.queryForMap(sqlMembresia, idActual);
        var nuevaMemb = jdbcTemplate.queryForMap(sqlMembresia, dto.getIdNuevaMembresia());

        dto.setTipoMembresiaAnterior((String) actualMemb.get("nombre"));
        dto.setTipoMembresiaAnterior((String) actualMemb.get("nombre"));
        dto.setPrecioAnterior(((Number) actualMemb.get("precio")).doubleValue());
        dto.setTipoMembresiaNueva((String) nuevaMemb.get("nombre"));
        dto.setPrecioNuevo(((Number) nuevaMemb.get("precio")).doubleValue());

        int duracionDias = (int) nuevaMemb.get("duracion_dias");
        LocalDate nuevaVenc = vencActual.isBefore(LocalDate.now()) ?
                LocalDate.now().plusDays(duracionDias) :
                vencActual.plusDays(duracionDias);

        String sqlUpdate = "UPDATE Socio SET id_tipo_suscripcion_actual = ?, fecha_vencimiento = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, dto.getIdNuevaMembresia(), nuevaVenc, dto.getIdSocio());

        dto.setNuevaFechaVencimiento(nuevaVenc);
        dto.setMensaje("Membresía cambiada exitosamente");
        return dto;
    }

    public void validarSocioExiste(int id) {
        String sql = "SELECT COUNT(1) FROM Socio WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == 0) throw new RuntimeException("Socio no existe");
    }

    public void validarSocioActivo(int id) {
        String sql = "SELECT estado FROM Socio WHERE id = ?";
        String estado = jdbcTemplate.queryForObject(sql, String.class, id);
        if (!"activo".equals(estado)) throw new RuntimeException("Socio no activo");
    }

    public void validarNuevaMembresiaExiste(int id) {
        String sql = "SELECT COUNT(1) FROM TipoSuscripcion WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == 0) throw new RuntimeException("Membresía no existe");
    }

    private void validarNoEsLaMismaMembresia(CambioMembresiaDTO dto) {
        String sql = "SELECT id_tipo_suscripcion_actual FROM Socio WHERE id = ?";
        int actual = jdbcTemplate.queryForObject(sql, Integer.class, dto.getIdSocio());
        if (actual == dto.getIdNuevaMembresia())
            throw new RuntimeException("Ya tiene esa membresía activa");
    }
}