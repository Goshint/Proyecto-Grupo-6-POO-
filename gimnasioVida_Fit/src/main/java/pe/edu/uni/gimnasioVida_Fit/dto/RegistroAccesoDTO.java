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
    private String metodoIdentificacion;
    private Integer idPersonalRegistro;
    private Boolean permitido;
    private String mensaje;
    private String nombreSocio;
    private String estadoMembresia;
}