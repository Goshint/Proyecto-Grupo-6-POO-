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
    private Integer idNuevaMembresia;
    private Integer idPersonalRegistro;
    private LocalDate nuevaFechaVencimiento;
    private String tipoMembresiaAnterior;
    private Double precioAnterior;
    private String tipoMembresiaNueva;
    private Double precioNuevo;
    private String mensaje;
}