package com.daos.aero.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente {
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private Long dni;
	@Column(nullable = false)	
	private String nombre;
	@Column(nullable = false)	
	private String apellido;
	@Column(nullable = false, unique = true)	
	private String email;
	@Column(name = "Fecha de nacimiento", nullable = false)	
	private Date fechaNacimiento;
	
	//relaciones
	@ManyToOne
	@JoinColumn(name = "domicilio_id")
	private Domicilio domicilio;
	@OneToOne
	private Pasaporte pasaporte;
	

	//getter & setter
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDni() {
		return dni;
	}
	
	public void setDni(Long dni) {
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Domicilio getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	public Pasaporte getPasaporte() {
		return pasaporte;
	}
	
	public void setPasaporte(Pasaporte pasaporte) {
		this.pasaporte = pasaporte;
	}
}
