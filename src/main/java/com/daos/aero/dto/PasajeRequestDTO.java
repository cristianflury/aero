package com.daos.aero.dto;

import com.daos.aero.model.Pasaje;

public class PasajeRequestDTO {
	
	private Long dni;
	
	private Long nroVuelo;
	
	private Long nroAsiento;
	
	

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public Long getNroVuelo() {
		return nroVuelo;
	}

	public void setNroVuelo(Long nroVuelo) {
		this.nroVuelo = nroVuelo;
	}

	public Long getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(Long nroAsiento) {
		this.nroAsiento = nroAsiento;
	}
	
	public Pasaje toModel() {
		Pasaje pasaje = new Pasaje();
		
		
		return pasaje;
		
	}

}
