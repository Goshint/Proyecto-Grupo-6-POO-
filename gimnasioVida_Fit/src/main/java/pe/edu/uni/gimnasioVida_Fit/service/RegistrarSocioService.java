package pe.edu.uni.gimnasioVida_Fit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.gimnasioVida_Fit.dto.RegistrarSocioDTO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegistrarSocioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public RegistrarSocioDTO registrarSocio(RegistrarSocioDTO dto) {
        // Validaciones
        validarDatosCompletos(dto);
        validarPersonalExiste(dto.getIdPersonalRegistro());
        validarDniUnico(dto.getNumeroSocio()); // usamos numeroSocio como DNI
        validarMembresiaExisteYActiva(dto.getIdTipoSuscripcion());

        // Obtener duración en días de la membresía
        String sqlDuracion = "SELECT duracion_dias FROM TipoSuscripcion WHERE id = ?";
        Integer duracionDias = jdbcTemplate.queryForObject(sqlDuracion, Integer.class, dto.getIdTipoSuscripcion());

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(duracionDias);
        String numeroSocio = "SOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        int idSocio;
        if (dto.getIdSocio() != null && dto.getIdSocio() != 0) {
            // ID proporcionado
            validarIdSocioDisponible(dto.getIdSocio());
            jdbcTemplate.execute("SET IDENTITY_INSERT Socio ON");
            try {
                String sqlInsert = """
                    INSERT INTO Socio (id, numero_socio, nombre, apellido, email, telefono, fecha_registro, fecha_vencimiento, estado, id_tipo_suscripcion_actual)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'activo', ?)
                    """;
                jdbcTemplate.update(sqlInsert, dto.getIdSocio(), numeroSocio, dto.getNombre(), dto.getApellido(),
                        dto.getEmail(), dto.getTelefono(), fechaInicio, fechaFin, dto.getIdTipoSuscripcion());
                idSocio = dto.getIdSocio();
            } finally {
                jdbcTemplate.execute("SET IDENTITY_INSERT Socio OFF");
            }
        } else {
            String sqlInsert = """
                INSERT INTO Socio (numero_socio, nombre, apellido, email, telefono, fecha_registro, fecha_vencimiento, estado, id_tipo_suscripcion_actual)
                VALUES (?, ?, ?, ?, ?, ?, ?, 'activo', ?)
                """;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, numeroSocio);
                ps.setString(2, dto.getNombre());
                ps.setString(3, dto.getApellido());
                ps.setString(4, dto.getEmail());
                ps.setString(5, dto.getTelefono());
                ps.setObject(6, fechaInicio);
                ps.setObject(7, fechaFin);
                ps.setInt(8, dto.getIdTipoSuscripcion());
                return ps;
            }, keyHolder);
            idSocio = keyHolder.getKey().intValue();
        }

        dto.setIdSocio(idSocio);
        dto.setNumeroSocio(numeroSocio);
        dto.setFechaRegistro(fechaInicio);
        dto.setFechaVencimiento(fechaFin);
        dto.setEstado("activo");
        dto.setMensaje("Socio registrado exitosamente");
        return dto;
    }

    private void validarDatosCompletos(RegistrarSocioDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isEmpty() ||
                dto.getApellido() == null || dto.getApellido().isEmpty() ||
                dto.getNumeroSocio() == null || dto.getNumeroSocio().isEmpty() ||
                dto.getIdTipoSuscripcion() == null) {
            throw new RuntimeException("Datos incompletos");
        }
    }

    public void validarDniUnico(String dni) {
        String sql = "SELECT COUNT(1) FROM Socio WHERE numero_socio = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        if (count > 0) throw new RuntimeException("DNI ya registrado");
    }

    public void validarMembresiaExisteYActiva(int id) {
        String sql = "SELECT COUNT(1) FROM TipoSuscripcion WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == 0) throw new RuntimeException("Membresía no existe");
    }

    public void validarPersonalExiste(int idPersonal) {
        if (idPersonal == 0) return; // opcional
        // Si no hay tabla Personal, omitir o lanzar excepción
    }

    private void validarIdSocioDisponible(int id) {
        String sql = "SELECT COUNT(1) FROM Socio WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count > 0) throw new RuntimeException("ID de socio ya existe");
    }
}