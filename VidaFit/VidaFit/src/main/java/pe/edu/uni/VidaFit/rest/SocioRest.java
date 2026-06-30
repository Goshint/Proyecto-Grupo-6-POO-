package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.SocioDTO;
import pe.edu.uni.VidaFit.service.SocioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/socios")
public class SocioRest {

    @Autowired
    private SocioService service;

    @PostMapping
    public int registrar(@RequestBody SocioDTO dto){
        return service.registrar(dto);
    }

    @GetMapping
    public List<Map<String,Object>> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public List<Map<String,Object>> buscarId(@PathVariable Integer id){
        return service.buscarId(id);
    }

    @GetMapping("/dni/{dni}")
    public List<Map<String,Object>> buscarDni(@PathVariable String dni){
        return service.buscarDni(dni);
    }

    @DeleteMapping("/{id}")
    public int eliminar(@PathVariable Integer id){
        return service.eliminar(id);
    }

}