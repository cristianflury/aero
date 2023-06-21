package com.daos.aero.dto;

import org.springframework.hateoas.RepresentationModel;

import com.daos.aero.model.Pasaje;

public class PasajeResponseDTO extends RepresentationModel<PasajeResponseDTO>{
	
	private Long numeroPasaje;
	
	private Double importe;
	
	private Long dniCliente;
	
	private Long nroVuelo;
	
	
	public PasajeResponseDTO(Pasaje pasaje) {
		super();
		
		this.numeroPasaje = pasaje.getNumero();
		this.importe = pasaje.getImporte();
		this.dniCliente = pasaje.getCliente().getDni();
		this.nroVuelo = pasaje.getVuelo().getNro();
		
	}

	public Long getNumeroPasaje() {
		return numeroPasaje;
	}

	public void setNumeroPasaje(Long numeroPasaje) {
		this.numeroPasaje = numeroPasaje;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Long getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(Long dniCliente) {
		this.dniCliente = dniCliente;
	}

	public Long getNroVuelo() {
		return nroVuelo;
	}

	public void setNroVuelo(Long nroVuelo) {
		this.nroVuelo = nroVuelo;
	}
	
	
	
	

}
