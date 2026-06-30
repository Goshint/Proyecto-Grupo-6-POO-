package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.ClaseDTO;
import pe.edu.uni.VidaFit.service.ClaseService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clases")
public class ClaseRest {

    @Autowired
    private ClaseService service;

    @PostMapping
    public int registrar(@RequestBody ClaseDTO dto){
        return service.registrar(dto);
    }

    @GetMapping
    public List<Map<String,Object>> listar(){
        return service.listar();
    }

}