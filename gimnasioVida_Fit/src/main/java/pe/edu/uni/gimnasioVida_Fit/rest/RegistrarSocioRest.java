package pe.edu.uni.gimnasioVida_Fit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.gimnasioVida_Fit.dto.RegistrarSocioDTO;
import pe.edu.uni.gimnasioVida_Fit.service.RegistrarSocioService;

@RestController
@RequestMapping("/api/socios")
public class RegistrarSocioRest {

    @Autowired
    private RegistrarSocioService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistrarSocioDTO dto) {
        try {
            RegistrarSocioDTO result = service.registrarSocio(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/dni/{dni}")
    public ResponseEntity<String> validarDni(@PathVariable String dni) {
        try {
            service.validarDniUnico(dni);
            return ResponseEntity.ok("DNI disponible");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/membresia/{id}")
    public ResponseEntity<String> validarMembresia(@PathVariable int id) {
        try {
            service.validarMembresiaExisteYActiva(id);
            return ResponseEntity.ok("Membresía válida");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/personal/{id}")
    public ResponseEntity<String> validarPersonal(@PathVariable int id) {
        try {
            service.validarPersonalExiste(id);
            return ResponseEntity.ok("Personal existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}