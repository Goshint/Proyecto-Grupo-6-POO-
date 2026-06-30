package pe.edu.uni.VidaFit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarError(Exception ex){

        ErrorResponse error = new ErrorResponse();

        error.setFecha(LocalDateTime.now());
        error.setCodigo(HttpStatus.BAD_REQUEST.value());
        error.setError("ERROR");
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

}