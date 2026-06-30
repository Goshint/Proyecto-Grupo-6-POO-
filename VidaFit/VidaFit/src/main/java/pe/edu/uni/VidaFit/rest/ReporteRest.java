package pe.edu.uni.VidaFit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.VidaFit.service.ReporteService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
public class ReporteRest {

    @Autowired
    private ReporteService service;

    @GetMapping("/socios-activos")
    public List<Map<String,Object>> sociosActivos(){
        return service.sociosActivos();
    }

    @GetMapping("/socios-vencidos")
    public List<Map<String,Object>> sociosVencidos(){
        return service.sociosVencidos();
    }

    @GetMapping("/pagos")
    public List<Map<String,Object>> pagos(){
        return service.pagos();
    }

    @GetMapping("/clases")
    public List<Map<String,Object>> clases(){
        return service.clases();
    }

}