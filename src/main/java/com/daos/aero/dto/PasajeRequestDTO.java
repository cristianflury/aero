package com.daos.aero.dto;



public class PasajeRequestDTO {
	
	private Long dni;
	
	private Long nroVuelo;
	
	private Integer nroAsiento;
	
	

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

	public Integer getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(Integer nroAsiento) {
		this.nroAsiento = nroAsiento;
	}
	
}
