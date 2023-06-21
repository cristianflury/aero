package com.daos.aero.excepcion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	
	@ExceptionHandler(PasajeException.class)
    public ResponseEntity<ErrorResponse> handlePasajeException(PasajeException ex) {
    	
        ErrorResponse errorResponse = new ErrorResponse(ex.getMensaje());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
    
	@ExceptionHandler(ClienteException.class)
    public ResponseEntity<ErrorResponse> handlePasajeException(ClienteException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMensaje());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
