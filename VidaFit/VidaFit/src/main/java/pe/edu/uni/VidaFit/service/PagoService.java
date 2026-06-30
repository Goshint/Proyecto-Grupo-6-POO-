package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.PagoDTO;

import java.util.List;
import java.util.Map;

@Service
public class PagoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registrar(PagoDTO dto){

        String sql="""
                INSERT INTO Pago
                (
                id_socio,
                monto,
                fecha_pago,
                metodo_pago,
                observacion
                )
                VALUES(?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getIdSocio(),
                dto.getMonto(),
                dto.getFechaPago(),
                dto.getMetodoPago(),
                dto.getObservacion());

    }

    public List<Map<String,Object>> listar(){

        return jdbcTemplate.queryForList(
                "SELECT * FROM Pago ORDER BY fecha_pago DESC");

    }

}