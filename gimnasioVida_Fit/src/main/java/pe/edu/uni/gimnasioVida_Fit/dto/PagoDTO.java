package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer idPago;
    private Integer idSocio;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private LocalDate nuevaFechaVencimiento;
    private String metodoPago;
    private Integer idTipoSuscripcion;     // nueva suscripción (si cambia)
    private Integer idPersonalRegistro;
}