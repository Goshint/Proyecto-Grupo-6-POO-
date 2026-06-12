package pe.edu.uni.gimnasioVida_Fit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.gimnasioVida_Fit.dto.RegistroAccesoDTO;
import pe.edu.uni.gimnasioVida_Fit.service.RegistroAccesoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/acceso")
public class RegistroAccesoRest {

    @Autowired
    private RegistroAccesoService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAcceso(@RequestBody RegistroAccesoDTO dto) {
        try {
            RegistroAccesoDTO result = service.registrarAcceso(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/diario")
    public ResponseEntity<?> obtenerAccesosDiarios(@RequestParam(required = false) String fecha) {
        try {
            if (fecha == null || fecha.isEmpty())
                fecha = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            List<Map<String, Object>> accesos = service.obtenerAccesosDiarios(fecha);
            return ResponseEntity.ok(accesos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<?> obtenerAccesosSocioHoy(@PathVariable int idSocio) {
        try {
            List<Map<String, Object>> accesos = service.obtenerAccesosSocioHoy(idSocio);
            return ResponseEntity.ok(accesos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}