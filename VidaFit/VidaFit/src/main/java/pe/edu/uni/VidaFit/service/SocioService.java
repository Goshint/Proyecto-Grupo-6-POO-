package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.SocioDTO;

import java.util.List;
import java.util.Map;

@Service
public class SocioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Registrar socio
    public int registrar(SocioDTO dto){

        String sql = """
                INSERT INTO Socio
                (
                numero_socio,
                nombre,
                apellido,
                dni,
                telefono,
                email,
                fecha_registro,
                fecha_vencimiento,
                estado,
                id_tipo
                )
                VALUES(?,?,?,?,?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getNumeroSocio(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getFechaRegistro(),
                dto.getFechaVencimiento(),
                dto.getEstado(),
                dto.getIdTipo());

    }

    // Listar
    public List<Map<String,Object>> listar(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Socio ORDER BY id_socio");

    }

    // Buscar por DNI
    public List<Map<String,Object>> buscarDni(String dni){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Socio WHERE dni=?",
                dni);

    }

    // Buscar por ID
    public List<Map<String,Object>> buscarId(Integer id){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Socio WHERE id_socio=?",
                id);

    }

    // Eliminar

    public int eliminar(Integer id){

        return jdbcTemplate.update(
                "DELETE FROM Socio WHERE id_socio=?",
                id);

    }

}