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
        validarNoEsLaMismaMembresia(dto.getIdSocio(), dto.getIdNuevaMembresia());

        var socio = jdbcTemplate.queryForMap("SELECT id_tipo_suscripcion_actual, fecha_vencimiento FROM Socio WHERE id = ?", dto.getIdSocio());
        int idActual = (int) socio.get("id_tipo_suscripcion_actual");
        LocalDate vencAct = ((java.sql.Date) socio.get("fecha_vencimiento")).toLocalDate();

        var memActual = jdbcTemplate.queryForMap("SELECT nombre, precio FROM TipoSuscripcion WHERE id = ?", idActual);
        var memNueva = jdbcTemplate.queryForMap("SELECT nombre, precio, duracion_dias FROM TipoSuscripcion WHERE id = ?", dto.getIdNuevaMembresia());

        dto.setTipoMembresiaAnterior((String) memActual.get("nombre"));
        dto.setPrecioAnterior(((Number) memActual.get("precio")).doubleValue());
        dto.setTipoMembresiaNueva((String) memNueva.get("nombre"));
        dto.setPrecioNuevo(((Number) memNueva.get("precio")).doubleValue());

        int duracion = (int) memNueva.get("duracion_dias");
        LocalDate nuevaVenc = vencAct.isBefore(LocalDate.now()) ? LocalDate.now().plusDays(duracion) : vencAct.plusDays(duracion);

        jdbcTemplate.update("UPDATE Socio SET id_tipo_suscripcion_actual = ?, fecha_vencimiento = ? WHERE id = ?",
                dto.getIdNuevaMembresia(), nuevaVenc, dto.getIdSocio());

        dto.setNuevaFechaVencimiento(nuevaVenc);
        dto.setMensaje("Membresía cambiada");
        return dto;
    }

    public void validarSocioExiste(int id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Socio WHERE id = ?", Integer.class, id);
        if (count == null || count == 0) throw new RuntimeException("Socio no existe");
    }
    public void validarSocioActivo(int id) {
        String estado = jdbcTemplate.queryForObject("SELECT estado FROM Socio WHERE id = ?", String.class, id);
        if (!"activo".equals(estado)) throw new RuntimeException("Socio no activo");
    }
    public void validarNuevaMembresiaExiste(int id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM TipoSuscripcion WHERE id = ?", Integer.class, id);
        if (count == null || count == 0) throw new RuntimeException("Membresía no existe");
    }
    public void validarNoEsLaMismaMembresia(int idSocio, int idNueva) {
        Integer actual = jdbcTemplate.queryForObject("SELECT id_tipo_suscripcion_actual FROM Socio WHERE id = ?", Integer.class, idSocio);
        if (actual != null && actual.equals(idNueva)) throw new RuntimeException("Ya tiene esa membresía");
    }
}