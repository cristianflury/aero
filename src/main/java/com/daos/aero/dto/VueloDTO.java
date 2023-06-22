package com.daos.aero.dto;


import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.daos.aero.model.Vuelo;

import jakarta.persistence.Id;

public class VueloDTO extends RepresentationModel<VueloDTO> {

	@Id
	private Long nro;

	
	private Date fecha;

	private int nroFilas;
	private int nroAsientosPorFila;

	private String tipoVuelo;

	private String destino;

	public VueloDTO(Vuelo pojo) {
		super();
		this.nro = pojo.getNro();
		this.fecha = pojo.getFecha();
		this.nroFilas = pojo.getNroFilas();
		this.nroAsientosPorFila = pojo.getNroAsientosPorFila();
		this.tipoVuelo = pojo.getTipoVuelo();
		this.destino = pojo.getDestino();

	}

	public Long getNro() {
		return nro;
	}

	public void setNro(Long nro) {
		this.nro = nro;
	}

	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNroFilas() {
		return nroFilas;
	}

	public void setNroFilas(int nroFilas) {
		this.nroFilas = nroFilas;
	}

	public int getNroAsientosPorFila() {
		return nroAsientosPorFila;
	}

	public void setNroAsientosPorFila(int nroAsientosPorFila) {
		this.nroAsientosPorFila = nroAsientosPorFila;
	}

	public String getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo(String tipoVuelo) {
		this.tipoVuelo = tipoVuelo;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

}
