package pe.edu.uni.VidaFit.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MembresiaScheduler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void actualizarSociosVencidos(){

        String sql = """
                UPDATE Socio
                SET estado='INACTIVO'
                WHERE fecha_vencimiento < GETDATE()
                """;

        int registros = jdbcTemplate.update(sql);

        System.out.println("Scheduler ejecutado. Socios actualizados: " + registros);

    }

}