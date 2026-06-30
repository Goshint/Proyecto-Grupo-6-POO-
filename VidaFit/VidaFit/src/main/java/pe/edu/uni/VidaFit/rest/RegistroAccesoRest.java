package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.RegistroAccesoDTO;
import pe.edu.uni.VidaFit.service.RegistroAccesoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accesos")
public class RegistroAccesoRest {

    @Autowired
    private RegistroAccesoService service;

    @PostMapping
    public int registrar(@RequestBody RegistroAccesoDTO dto){
        return service.registrar(dto);
    }

    @GetMapping
    public List<Map<String,Object>> listar(){
        return service.listar();
    }

}