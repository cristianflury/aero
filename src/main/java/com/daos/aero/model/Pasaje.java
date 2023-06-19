package com.daos.aero.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pasaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	@Column (name = "nro_asiento" , nullable = false)
	private Long nroAsiento;
	
	@Column (nullable = false)
	private Double importe;
	
	@ManyToOne
	@JoinColumn(name = "cliente_dni", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "vuelo_nro", nullable = false)
	private Vuelo vuelo;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(Long nroAsiento) {
		this.nroAsiento = nroAsiento;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}
	

	

}
