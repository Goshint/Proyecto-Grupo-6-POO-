package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarSocioDTO {
    private Integer idSocio;          // para respuesta
    private String numeroSocio;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private LocalDate fechaVencimiento;
    private String estado;
    private Integer idTipoSuscripcion;  // de entrada
    private Integer idPersonalRegistro; // opcional, para auditoría
}