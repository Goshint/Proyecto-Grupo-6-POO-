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
    private Integer idSocio;
    private String numeroSocio;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private LocalDate fechaVencimiento;
    private String estado;
    private Integer idTipoSuscripcion;
    private Integer idPersonalRegistro;
    private String mensaje;
}