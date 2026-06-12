package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscribirDTO {
    private Integer idInscripcion;
    private Integer idSocio;
    private Integer idClase;
    private LocalDateTime fechaInscripcion;
    private Integer idPersonalRegistro;
    private String mensaje;
    private Integer cupoRestante;
}