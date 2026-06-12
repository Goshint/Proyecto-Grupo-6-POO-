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
public class CambioMembresiaDTO {
    private Integer idSocio;
    private Integer idNuevaMembresia;   // id_tipo_suscripcion
    private Integer idPersonalRegistro;
    private LocalDate nuevaFechaVencimiento; // para respuesta
}