package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.InscribirDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class InscribirService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public InscribirDTO inscribir(InscribirDTO dto) {
        validarSocioActivoConMembresia(dto.getIdSocio());
        validarClaseExiste(dto.getIdClase());
        validarCupoDisponible(dto.getIdClase());
        validarInscripcionUnica(dto.getIdSocio(), dto.getIdClase());

        jdbcTemplate.update("INSERT INTO InscripcionClase (id_socio, id_clase, fecha_inscripcion) VALUES (?, ?, ?)",
                dto.getIdSocio(), dto.getIdClase(), LocalDateTime.now());

        jdbcTemplate.update("UPDATE Clase SET cupo_maximo = cupo_maximo - 1 WHERE id = ?", dto.getIdClase());

        int cupo = jdbcTemplate.queryForObject("SELECT cupo_maximo FROM Clase WHERE id = ?", Integer.class, dto.getIdClase());
        dto.setCupoRestante(cupo);
        dto.setMensaje("Inscripción exitosa");
        return dto;
    }

    // Métodos públicos para los endpoints de validación
    public void validarSocioActivoConMembresia(int idSocio) {
        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT estado, fecha_vencimiento FROM Socio WHERE id = ?", idSocio);
        String estado = (String) row.get("estado");
        LocalDate venc = ((java.sql.Date) row.get("fecha_vencimiento")).toLocalDate();
        if (!"activo".equals(estado) || venc.isBefore(LocalDate.now()))
            throw new RuntimeException("Socio inactivo o vencido");
    }

    public void validarClaseExiste(int idClase) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Clase WHERE id = ?", Integer.class, idClase);
        if (count == null || count == 0) throw new RuntimeException("Clase no existe");
    }

    public void validarCupoDisponible(int idClase) {
        Integer cupo = jdbcTemplate.queryForObject("SELECT cupo_maximo FROM Clase WHERE id = ?", Integer.class, idClase);
        if (cupo == null || cupo <= 0) throw new RuntimeException("No hay cupo disponible");
    }

    private void validarInscripcionUnica(int idSocio, int idClase) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM InscripcionClase WHERE id_socio = ? AND id_clase = ?", Integer.class, idSocio, idClase);
        if (count != null && count > 0) throw new RuntimeException("Ya inscrito en esta clase");
    }
}