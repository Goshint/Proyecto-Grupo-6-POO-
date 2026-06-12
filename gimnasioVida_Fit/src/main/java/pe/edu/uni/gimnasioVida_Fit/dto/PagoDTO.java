package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoDTO {
    private String numeroSocio;
    private BigDecimal monto;
    private String metodoPago; // efectivo, tarjeta, transferencia
    private Integer idNuevoTipoSuscripcion; // opcional
}