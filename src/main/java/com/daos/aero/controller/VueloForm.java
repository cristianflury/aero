package com.daos.aero.controller;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.daos.aero.model.Vuelo;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class VueloForm {
	
	@NotNull(message = "el número de vuelo no puede ser nulo")
	private Long nro;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	@NotNull
	@FutureOrPresent(message="la fecha debe ser actual o futura")
	private Date fecha;
	@Max(value=30, message = "demasiadas filas")
	private int nroFilas;
	@Max(value=8, message = "demasiados asientos por fila")
	private int nroAsientosPorFila;
	private String tipoVuelo;
	@Nonnull
	private String destino;
	
	//Get and Set
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
	
	public Vuelo toPojo() 
	{
		Vuelo v = new Vuelo();
		v.setNro(this.getNro());
		v.setFecha(this.getFecha());
		v.setDestino(this.getDestino());
		v.setNroFilas(this.getNroFilas());
		v.setNroAsientosPorFila(this.getNroAsientosPorFila());
		v.setTipoVuelo(this.getTipoVuelo());
		return v;
	}
	

}
