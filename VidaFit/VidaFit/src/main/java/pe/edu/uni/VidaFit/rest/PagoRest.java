package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.dto.PagoDTO;
import pe.edu.uni.VidaFit.service.PagoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
public class PagoRest {

    @Autowired
    private PagoService service;

    @PostMapping
    public int registrar(@RequestBody PagoDTO dto){
        return service.registrar(dto);
    }

    @GetMapping
    public List<Map<String,Object>> listar(){
        return service.listar();
    }

}