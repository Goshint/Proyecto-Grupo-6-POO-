package pe.edu.uni.gimnasioVida_Fit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.gimnasioVida_Fit.dto.InscribirDTO;
import pe.edu.uni.gimnasioVida_Fit.service.InscribirService;

@RestController
@RequestMapping("/api/consultar")
public class InscribirRest {

    @Autowired
    private InscribirService service;

    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribir(@RequestBody InscribirDTO dto) {
        try {
            InscribirDTO result = service.inscribir(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/socio/{idSocio}")
    public ResponseEntity<String> validarSocio(@PathVariable int idSocio) {
        try {
            service.validarSocioActivoConMembresia(idSocio);
            return ResponseEntity.ok("Socio válido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/clase/{idClase}")
    public ResponseEntity<String> validarClase(@PathVariable int idClase) {
        try {
            service.validarClaseExiste(idClase);
            return ResponseEntity.ok("Clase existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/capacidad/{idClase}")
    public ResponseEntity<String> validarCapacidad(@PathVariable int idClase) {
        try {
            service.validarCupoDisponible(idClase);
            return ResponseEntity.ok("Cupo disponible");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}