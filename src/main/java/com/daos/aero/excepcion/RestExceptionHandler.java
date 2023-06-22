package com.daos.aero.excepcion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	
	@ExceptionHandler(VueloException.class)
    public ResponseEntity<ErrorResponse> handlePasajeException(VueloException ex) {
    	
        ErrorResponse errorResponse = new ErrorResponse(ex.getMensaje());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

}
