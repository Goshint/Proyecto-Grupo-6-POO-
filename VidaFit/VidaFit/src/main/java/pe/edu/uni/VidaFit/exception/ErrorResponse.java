package pe.edu.uni.VidaFit.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime fecha;
    private int codigo;
    private String error;
    private String mensaje;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime fecha, int codigo, String error, String mensaje) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.error = error;
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}