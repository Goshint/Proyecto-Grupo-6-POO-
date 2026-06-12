package pe.edu.uni.gimnasioVida_Fit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.gimnasioVida_Fit.dto.PagoDTO;
import pe.edu.uni.gimnasioVida_Fit.service.PagoService;

@RestController
@RequestMapping("/api/pago")
public class PagoRest {

    @Autowired
    private PagoService service;

    @PostMapping("/suscripcion")
    public ResponseEntity<?> registrarPago(@RequestBody PagoDTO dto) {
        try {
            PagoDTO result = service.registrarPago(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/suscripcion/{idTipo}")
    public ResponseEntity<String> validarSuscripcion(@PathVariable int idTipo) {
        try {
            service.validarSuscripcionExisteYActiva(idTipo);
            return ResponseEntity.ok("Tipo suscripción válido");
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

    @GetMapping("/validar/metodo-pago/{metodo}")
    public ResponseEntity<String> validarMetodoPago(@PathVariable String metodo) {
        try {
            service.validarMetodoPago(metodo);
            return ResponseEntity.ok("Método válido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validar/monto/{idTipo}/{monto}")
    public ResponseEntity<String> validarMonto(@PathVariable int idTipo, @PathVariable double monto) {
        try {
            service.validarMontoPago(idTipo, monto);
            return ResponseEntity.ok("Monto correcto");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}