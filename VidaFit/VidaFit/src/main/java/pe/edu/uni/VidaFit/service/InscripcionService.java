package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.InscripcionDTO;

import java.util.List;
import java.util.Map;

@Service
public class InscripcionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registrar(InscripcionDTO dto){

        String sql="""
                INSERT INTO Inscripcion
                (
                id_socio,
                id_clase,
                fecha_inscripcion
                )
                VALUES(?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getIdSocio(),
                dto.getIdClase(),
                dto.getFechaInscripcion());

    }

    public List<Map<String,Object>> listar(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Inscripcion");

    }

}