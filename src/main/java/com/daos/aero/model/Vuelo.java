package com.daos.aero.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vuelo {

	@Id
	@Nonnull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Número de Vuelo", nullable = false)
	private Long nro;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	@Column(name = "Fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "Número de Filas")
	private int nroFilas;
	@Column(name = "Número de Asientos por Fila")
	private int nroAsientosPorFila;
	@Column(name = "Tipo de Vuelo")
	private String tipoVuelo;
	@Nonnull
	private String destino;
	private String origen;
	private String estado;

	// Getters and Setters
	public long getNro() {
		return nro;
	}

	public void setNro(long nro) {
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

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
