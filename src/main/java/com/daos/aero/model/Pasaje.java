package com.daos.aero.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Entity
public class Pasaje {
	
	//@Id
	//@GeneratedValue
	private UUID nroPasaje;
	
	private Long nroAsiento;
	

	public UUID getNroPasaje() {
		return nroPasaje;
	}

	public void setNroPasaje(UUID nroPasaje) {
		this.nroPasaje = nroPasaje;
	}

	public Long getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(Long nroAsiento) {
		this.nroAsiento = nroAsiento;
	}
	

}
