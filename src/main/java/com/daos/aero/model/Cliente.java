package com.daos.aero.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
public class Cliente {
	//atributos
	@Id
	private Long dni;

	@Column(nullable = false)
	@Size(min = 1,max = 30, message = "El nombre es demasiado largo o inválido.")
	private String nombre;

	@Column(nullable = false)
	@Size(min = 1,max = 30, message = "El apellido es demasiado largo o inválido.")
	private String apellido;

	@Column(nullable = false, unique = true)
	@Email(message = "El e-mail ingresado no es valido.")
	private String email;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	private String numeroPasaporte;

	@Temporal(TemporalType.DATE)
	private Date vencimientoPasaporte;
	
	//relaciones
	@OneToOne
	//@JoinColumn(name = "domicilio_id")
	private Domicilio domicilio;
	
	//getter & setter
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

	public String getNumeroPasaporte() {
		return numeroPasaporte;
	}

	public void setNumeroPasaporte(String numeroPasaporte) {
		this.numeroPasaporte = numeroPasaporte;
	}

	public Date getVencimientoPasaporte() {
		return vencimientoPasaporte;
	}

	public void setVencimientoPasaporte(Date vencimientoPasaporte) {
		this.vencimientoPasaporte = vencimientoPasaporte;
	}
	
}
