package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AlertaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> membresiasPorVencer(){

        String sql="""
            SELECT *
            FROM Socio
            WHERE DATEDIFF(day,GETDATE(),fecha_vencimiento)<=5
            AND estado='ACTIVO'
            """;

        return jdbcTemplate.queryForList(sql);

    }

}