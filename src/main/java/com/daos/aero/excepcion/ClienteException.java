package com.daos.aero.excepcion;

import org.springframework.http.HttpStatus;

public class ClienteException extends Exception{
	private String mensaje;
	private HttpStatus status;
	
	public ClienteException(String mensaje, HttpStatus status) {		
		this.mensaje = mensaje;
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
