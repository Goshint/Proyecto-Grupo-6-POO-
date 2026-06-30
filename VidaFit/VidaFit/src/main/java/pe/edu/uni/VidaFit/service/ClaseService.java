package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.ClaseDTO;

import java.util.List;
import java.util.Map;

@Service
public class ClaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registrar(ClaseDTO dto){

        String sql="""
                INSERT INTO Clase
                (
                nombre,
                instructor,
                horario,
                capacidad,
                estado
                )
                VALUES(?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getNombre(),
                dto.getInstructor(),
                dto.getHorario(),
                dto.getCapacidad(),
                dto.getEstado());

    }

    public List<Map<String,Object>> listar(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Clase");

    }

}