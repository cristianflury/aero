package com.daos.aero.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.daos.aero.model.Cliente;
import com.daos.aero.model.Domicilio;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ClienteDTO extends RepresentationModel<ClienteDTO>{
	//atributos
	@Id
	@NotNull
	private Long dni;

	@NotNull
	@Size(min = 1,max = 30, message = "El nombre es demasiado largo o inválido.")
	private String nombre;	

	@NotNull
	@Size(min = 1,max = 30, message = "El apellido es demasiado largo o inválido.")
	private String apellido;	

	@NotNull
	@Email(message = "El e-mail ingresado no es valido.")
	private String email;	

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nonnull
	private Date nacimiento;

	@Nonnull
	private Domicilio domicilio; //cambiar por Long idDomicilio

	private String numeroPasaporte;

	private Date vencimientoPasaporte;

	
	//constructores
	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente pojo) {
		super();
		this.dni = pojo.getDni();
		this.nombre = pojo.getNombre();
		this.apellido = pojo.getApellido();
		this.email = pojo.getEmail();
		this.nacimiento = pojo.getFechaNacimiento();
		this.domicilio = pojo.getDomicilio();
		this.numeroPasaporte = pojo.getNumeroPasaporte();
		this.vencimientoPasaporte = pojo.getVencimientoPasaporte();
	}
	
	//toPojo
	public Cliente toPojo() {
		Cliente c = new Cliente();
		c.setDni(dni);
		c.setNombre(nombre);
		c.setApellido(apellido);
		c.setEmail(email);
		c.setFechaNacimiento(nacimiento);
		c.setDomicilio(domicilio);
		c.setNumeroPasaporte(numeroPasaporte);
		c.setVencimientoPasaporte(vencimientoPasaporte);
		
		return c;
	}

	//getters & setters
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

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
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
