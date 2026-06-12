package pe.edu.uni.gimnasioVida_Fit.dto;

import lombok.Data;

@Data
public class RegistrarSocioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer idTipoSuscripcion;
}