package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.InscribirDTO;

import java.time.LocalDateTime;

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

        String sqlInsert = "INSERT INTO InscripcionClase (id_socio, id_clase, fecha_inscripcion) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsert, dto.getIdSocio(), dto.getIdClase(), LocalDateTime.now());

        // Actualizar cupo de la clase (si manejas cupo como contador)
        String sqlUpdateCupo = "UPDATE Clase SET cupo_maximo = cupo_maximo - 1 WHERE id = ?";
        jdbcTemplate.update(sqlUpdateCupo, dto.getIdClase());

        // Obtener cupo restante
        String sqlCupo = "SELECT cupo_maximo FROM Clase WHERE id = ?";
        int cupoRest = jdbcTemplate.queryForObject(sqlCupo, Integer.class, dto.getIdClase());
        dto.setCupoRestante(cupoRest);
        dto.setMensaje("Inscripción exitosa");
        return dto;
    }

    public void validarSocioActivoConMembresia(int idSocio) {
        String sql = "SELECT estado, fecha_vencimiento FROM Socio WHERE id = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, idSocio);
        String estado = (String) row.get("estado");
        LocalDate venc = ((java.sql.Date) row.get("fecha_vencimiento")).toLocalDate();
        if (!"activo".equals(estado) || venc.isBefore(LocalDate.now()))
            throw new RuntimeException("Socio inactivo o membresía vencida");
    }

    public void validarClaseExiste(int idClase) {
        String sql = "SELECT COUNT(1) FROM Clase WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idClase);
        if (count == 0) throw new RuntimeException("Clase no existe");
    }

    public void validarCupoDisponible(int idClase) {
        String sql = "SELECT cupo_maximo FROM Clase WHERE id = ?";
        int cupo = jdbcTemplate.queryForObject(sql, Integer.class, idClase);
        if (cupo <= 0) throw new RuntimeException("No hay cupo disponible");
    }

    private void validarInscripcionUnica(int idSocio, int idClase) {
        String sql = "SELECT COUNT(1) FROM InscripcionClase WHERE id_socio = ? AND id_clase = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idSocio, idClase);
        if (count > 0) throw new RuntimeException("Ya inscrito en esta clase");
    }
}