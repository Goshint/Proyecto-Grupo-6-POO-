package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.InscripcionDTO;
import pe.edu.uni.VidaFit.service.InscripcionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionRest {

    @Autowired
    private InscripcionService service;

    @PostMapping
    public int registrar(@RequestBody InscripcionDTO dto){
        return service.registrar(dto);
    }

    @GetMapping
    public List<Map<String,Object>> listar(){
        return service.listar();
    }

}