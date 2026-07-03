package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.LoginDTO;
import pe.edu.uni.VidaFit.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginRest {

    @Autowired
    private LoginService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody LoginDTO dto){

        String rol = service.login(dto);

        if (rol == null) {
            return null;
        }

        return "\"" + rol + "\"";

    }

}
