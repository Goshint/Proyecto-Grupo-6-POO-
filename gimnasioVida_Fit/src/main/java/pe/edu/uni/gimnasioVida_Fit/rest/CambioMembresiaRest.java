package pe.edu.uni.gimnasioVida_Fit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.gimnasioVida_Fit.dto.CambioMembresiaDTO;
import pe.edu.uni.gimnasioVida_Fit.service.CambioMembresiaService;

@RestController
@RequestMapping("/api/procesos")
public class CambioMembresiaRest {

    @Autowired
    private CambioMembresiaService service;

    @PostMapping("/membresia")
    public ResponseEntity<?> cambiarMembresia(@RequestBody CambioMembresiaDTO dto) {
        try {
            CambioMembresiaDTO result = service.cambiarMembresia(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/socio-existe/{idSocio}")
    public ResponseEntity<String> validarSocioExiste(@PathVariable int idSocio) {
        try {
            service.validarSocioExiste(idSocio);
            return ResponseEntity.ok("Socio existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/socio-activo/{idSocio}")
    public ResponseEntity<String> validarSocioActivo(@PathVariable int idSocio) {
        try {
            service.validarSocioActivo(idSocio);
            return ResponseEntity.ok("Socio activo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/membresia-existe/{idMembresia}")
    public ResponseEntity<String> validarMembresiaExiste(@PathVariable int idMembresia) {
        try {
            service.validarNuevaMembresiaExiste(idMembresia);
            return ResponseEntity.ok("Membresía existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}