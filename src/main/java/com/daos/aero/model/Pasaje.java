package com.daos.aero.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Pasaje {
	
	@Id
	@GeneratedValue
	private UUID nroPasaje;
	
	private Long dni;
	
	private Long nroVuelo;
	
	private Long nroAsiento;
	
	

}
