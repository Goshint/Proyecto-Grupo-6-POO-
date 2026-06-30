package pe.edu.uni.VidaFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.VidaFit.dto.LoginDTO;

@Service
public class LoginService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String login(LoginDTO dto){

        String sql = """
                SELECT rol
                FROM Usuario
                WHERE usuario = ?
                AND password = ?
                """;

        try{

            return jdbcTemplate.queryForObject(
                    sql,
                    String.class,
                    dto.getUsuario(),
                    dto.getPassword()
            );

        }catch(Exception e){

            return null;

        }

    }

}