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
public class RegistroAccesoDTO {
    private Integer idAcceso;
    private Integer idSocio;
    private LocalDateTime fechaHoraAcceso;
    private String metodoIdentificacion; // manual, barras, qr
    private Integer idPersonalRegistro;
    private Boolean permitido;    // solo para respuesta
    private String mensaje;
}