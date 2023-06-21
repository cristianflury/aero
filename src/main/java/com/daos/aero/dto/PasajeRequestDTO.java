package com.daos.aero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasajeRequestDTO {
	
	@JsonProperty("dni")
	private Long dni;
	
	@JsonProperty("nro_vuelo")
	private Long nroVuelo;
	
	@JsonProperty("nro_asiento")
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
