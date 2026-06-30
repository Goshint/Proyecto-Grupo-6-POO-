package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> sociosActivos(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Socio WHERE estado='ACTIVO'");

    }

    public List<Map<String,Object>> sociosVencidos(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Socio WHERE fecha_vencimiento<GETDATE()");

    }

    public List<Map<String,Object>> pagos(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Pago");

    }

    public List<Map<String,Object>> clases(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Clase");

    }

}