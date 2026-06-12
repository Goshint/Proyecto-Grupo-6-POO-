package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistroAccesoDTO {
    private boolean permitido;
    private String mensaje;
    private String nombreCompleto;
    private String fechaVencimiento;
}