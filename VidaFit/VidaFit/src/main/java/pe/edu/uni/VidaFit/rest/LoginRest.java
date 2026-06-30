package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.LoginDTO;
import pe.edu.uni.VidaFit.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginRest {

    @Autowired
    private LoginService service;

    @PostMapping
    public String login(@RequestBody LoginDTO dto){

        return service.login(dto);

    }

}