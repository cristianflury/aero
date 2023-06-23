package com.daos.aero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class PasajeRequestDTO {
	
	@NotNull( message = "El dni no puede ser nulo")
	@JsonProperty("dni")
	private Long dni;
	
	@NotNull( message = "El número de vuelo no puede ser nulo")
	@JsonProperty("nro_vuelo")
	private Long nroVuelo;
	
	@NotNull( message = "El número de asiento no puede ser nulo")
	@Max(value = 240, message = "El número de asiento no puede ser mayor a 240")
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
