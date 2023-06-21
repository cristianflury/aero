package com.daos.aero.dto;

import org.springframework.hateoas.RepresentationModel;

import com.daos.aero.model.Domicilio;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class DomicilioDTO extends RepresentationModel<DomicilioDTO>{
	//atributos
	@Id
	@NotNull
	private Long id;
	private String numero;
	private String calle;
	private String ciudad;
	
	//constructores
	public DomicilioDTO() {
		super();
	}
	
	public DomicilioDTO(Domicilio pojo) {
		super();
		this.id = 0L;
		this.numero = pojo.getNumero();
		this.calle = pojo.getCalle();
		this.ciudad = pojo.getCiudad();
	}

	//toPojo
	public Domicilio toPojo() {
		Domicilio d = new Domicilio();
		d.setId(id);
		d.setNumero(numero);
		d.setCalle(calle);
		d.setCiudad(ciudad);
		return d;
	}
	
	//getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}
