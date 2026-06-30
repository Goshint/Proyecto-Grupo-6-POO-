package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.RegistroAccesoDTO;

import java.util.List;
import java.util.Map;

@Service
public class RegistroAccesoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registrar(RegistroAccesoDTO dto){

        String sql="""
                INSERT INTO RegistroAcceso
                (
                id_socio,
                fecha_hora,
                resultado,
                observacion
                )
                VALUES(?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getIdSocio(),
                dto.getFechaHora(),
                dto.getResultado(),
                dto.getObservacion());

    }

    public List<Map<String,Object>> listar(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM RegistroAcceso");

    }

}