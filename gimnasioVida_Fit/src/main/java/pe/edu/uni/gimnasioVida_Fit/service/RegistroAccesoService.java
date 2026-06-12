package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.RegistroAccesoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RegistroAccesoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public RegistroAccesoDTO registrarAcceso(RegistroAccesoDTO dto) {
        validarSocioActivoConMembresia(dto.getIdSocio());
        validarAccesoUnicoHoy(dto.getIdSocio());
        validarPersonalExiste(dto.getIdPersonalRegistro());

        String sql = "INSERT INTO RegistroAcceso (id_socio, fecha_hora_acceso, metodo_identificacion) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dto.getIdSocio(), LocalDateTime.now(), dto.getMetodoIdentificacion());

        dto.setPermitido(true);
        dto.setMensaje("Acceso registrado");
        dto.setFechaHoraAcceso(LocalDateTime.now());
        return dto;
    }

    public List<Map<String, Object>> obtenerAccesosDiarios(String fecha) {
        String sql = """
            SELECT ra.id_socio, s.nombre + ' ' + s.apellido as nombreSocio, ra.fecha_hora_acceso, ra.metodo_identificacion
            FROM RegistroAcceso ra
            JOIN Socio s ON ra.id_socio = s.id
            WHERE CAST(ra.fecha_hora_acceso AS DATE) = ?
            ORDER BY ra.fecha_hora_acceso DESC
            """;
        return jdbcTemplate.queryForList(sql, fecha);
    }

    public List<Map<String, Object>> obtenerAccesosSocioHoy(int idSocio) {
        String sql = """
            SELECT fecha_hora_acceso, metodo_identificacion
            FROM RegistroAcceso
            WHERE id_socio = ? AND CAST(fecha_hora_acceso AS DATE) = CAST(GETDATE() AS DATE)
            ORDER BY fecha_hora_acceso DESC
            """;
        return jdbcTemplate.queryForList(sql, idSocio);
    }

    private void validarSocioActivoConMembresia(int idSocio) {
        String sql = "SELECT estado, fecha_vencimiento FROM Socio WHERE id = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, idSocio);
        String estado = (String) row.get("estado");
        LocalDate venc = ((java.sql.Date) row.get("fecha_vencimiento")).toLocalDate();
        if (!"activo".equals(estado) || venc.isBefore(LocalDate.now()))
            throw new RuntimeException("Socio inactivo o membresía vencida");
    }

    private void validarAccesoUnicoHoy(int idSocio) {
        String sql = "SELECT COUNT(1) FROM RegistroAcceso WHERE id_socio = ? AND CAST(fecha_hora_acceso AS DATE) = CAST(GETDATE() AS DATE)";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idSocio);
        if (count > 0) throw new RuntimeException("Ya registró acceso hoy");
    }

    private void validarPersonalExiste(Integer id) {}
}