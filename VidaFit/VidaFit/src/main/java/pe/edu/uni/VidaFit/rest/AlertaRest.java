package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.service.AlertaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alertas")
public class AlertaRest {

    @Autowired
    private AlertaService service;

    @GetMapping("/membresias")
    public List<Map<String,Object>> listar(){

        return service.membresiasPorVencer();

    }

}