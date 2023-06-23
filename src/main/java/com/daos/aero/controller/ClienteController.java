package com.daos.aero.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.dto.ClienteDTO;
import com.daos.aero.dto.ErrorDTO;
import com.daos.aero.excepcion.ClienteException;
import com.daos.aero.model.Cliente;
import com.daos.aero.service.IClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private IClienteService clienteService;
	
	/**
	 * Obtiene un cliente a través de su DNI
	 * @param dni
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value = "/{dni}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ClienteDTO> getById(@PathVariable Long dni) throws ClienteException{
		Optional<Cliente> rta = clienteService.getById(dni);
		if(rta.isPresent()) {
			Cliente pojo = rta.get();
			return new ResponseEntity<ClienteDTO>(buildResponse(pojo), HttpStatus.OK); //200 OK
		}
		
		throw new ClienteException("Cliente no encontrado.", HttpStatus.NOT_FOUND); //404 Not Found
	}
	
	/**
	 * Filtra todos los clientes y también por nombre y/o apellido. 
	 * @param apellido
	 * @param nombre
	 * @return
	 * @throws Excepcion 
	 */
	@GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE})
	public List<ClienteDTO> filtrarPersonas(@RequestParam(name = "nombre",required = false) String nombre , @RequestParam(name = "apellido",required = false) String apellido) throws ClienteException {
		List<Cliente> clientes = clienteService.filtrar(nombre, apellido);
		List<ClienteDTO> clienteDTO = new ArrayList<ClienteDTO>();
		for (Cliente pojo : clientes) { //URLs
	        clienteDTO.add(buildResponse(pojo));
		}
		return clienteDTO;
	}
	
	/**
	 * Guarda un nuevo cliente en la base de datos
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody ClienteDTO dto, BindingResult result) throws ClienteException{
		if(result.hasErrors()) {
			return buildError(result); 
		}
		
		Cliente cliente = dto.toPojo();
		Optional<Cliente> rtaCliente = clienteService.getById(cliente.getDni());
		if(rtaCliente.isPresent()) {
			if(rtaCliente.get().getDni().equals(cliente.getDni())) { //DNI es el identificador. No se puede repetir
				throw new ClienteException("El DNI indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			rtaCliente = clienteService.getByEmail(cliente.getEmail());
			if(rtaCliente.get().getEmail().equals(cliente.getEmail())) { //email es único. No se puede repetir.
				throw new ClienteException("El email indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
			}			
		}
		
				//validaciones
				if(cliente.getNombre() == null) {
					throw new ClienteException("El nombre no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
				}

				if(cliente.getApellido() == null) {
					throw new ClienteException("El apellido no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
				}
				
				if(cliente.getFechaNacimiento() == null) {
					throw new ClienteException("La fecha de nacimiento no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
				}
				
				if(cliente.getFechaNacimiento().after(new Date())){
					throw new ClienteException("La fecha de nacimiento no es válida.", HttpStatus.NOT_FOUND); //404 Not Found
				}

				
				if(cliente.getNumeroPasaporte() != null && cliente.getVencimientoPasaporte() == null) { //pasaporte sin fecha de vencimiento
					throw new ClienteException("La fecha de vencimiento del pasaporte no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
				}
				
				if(cliente.getDomicilio() == null) {
					throw new ClienteException("El domicilio no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
				}else if(cliente.getDomicilio().getCiudad() == null) {
					throw new ClienteException("La ciudad del domicilio no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
				}
				
		//guardo el cliente
		clienteService.guardar(cliente);
		
		return ResponseEntity.ok(buildResponse(cliente)); //200 OK
	}
	
	/**
	 * Modifica un cliente y domicilio existente en la base de datos
	 * @param dto
	 * @param dni
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/{dni}")
	public ResponseEntity<Object> actualizar(@RequestBody ClienteDTO dto, @PathVariable Long dni) throws ClienteException{
		Optional<Cliente> rta = clienteService.getById(dni);
		if(!rta.isPresent()) {
			throw new ClienteException("No se encontró el cliente que quiere actualizar.", HttpStatus.NOT_FOUND); //404 Not Found
		}else {
			Cliente cliente = dto.toPojo();
			if(!cliente.getDni().equals(dni)){//DNI es el identificador. No se puede modificar.
				throw new ClienteException("No puede modificar el DNI.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			if(!rta.get().getDomicilio().getId().equals(cliente.getDomicilio().getId())){ //ID del domicilio es el identificador. No se puede modificar.
				throw new ClienteException("No puede modificar el ID del domicilio.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			Optional<Cliente> rta2 = clienteService.getByEmail(cliente.getEmail());
			if(!rta.equals(rta2)) {
				if(rta2.isPresent() && rta2.get().getEmail().equals(cliente.getEmail())){ //email es único. No se puede repetir.
					throw new ClienteException("El email indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
				}
			}
			
			rta2 = clienteService.getByNumeroPasaporte(cliente.getNumeroPasaporte());
			if(!rta.equals(rta2)) {
				if(rta2.isPresent() && rta2.get().getNumeroPasaporte().equals(cliente.getNumeroPasaporte())){ //numero de pasaporte es único. No se puede repetir.
					throw new ClienteException("El número de pasaporte indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
				}
			}
			
			//validaciones
			if(cliente.getNombre() == null) {
				throw new ClienteException("El nombre no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
			}

			if(cliente.getApellido() == null) {
				throw new ClienteException("El apellido no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			if(cliente.getFechaNacimiento() == null) {
				throw new ClienteException("La fecha de nacimiento no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			if(cliente.getFechaNacimiento().after(new Date())){
				throw new ClienteException("La fecha de nacimiento no es válida.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			if(cliente.getNumeroPasaporte() != null && cliente.getVencimientoPasaporte() == null) { //pasaporte sin fecha de vencimiento
				throw new ClienteException("La fecha de vencimiento del pasaporte no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			if(cliente.getDomicilio() == null) {
				throw new ClienteException("El domicilio no puede ser nulo.", HttpStatus.NOT_FOUND); //404 Not Found
			}else if(cliente.getDomicilio().getCiudad() == null) {
				throw new ClienteException("La ciudad del domicilio no puede ser nula.", HttpStatus.NOT_FOUND); //404 Not Found
			}
			
			//actualizo el cliente
			clienteService.actualizar(cliente);
			
			return ResponseEntity.ok(buildResponse(cliente)); //200 OK
		}
	}
	
	
	/**
	 * Elimina un cliente existente en la base de datos.
	 * @param dni
	 * @return
	 */
	@DeleteMapping("/{dni}")
	public ResponseEntity<String> eliminar(@PathVariable Long dni) throws ClienteException{
		if(!clienteService.getById(dni).isPresent()) {
			throw new ClienteException("No existe un cliente con ese DNI.", HttpStatus.NOT_FOUND); //404 Not Found
		}
		
		//borro el cliente
		clienteService.eliminar(dni);
		
		return ResponseEntity.ok().build(); //200 OK
	}
	
	//Otros métodos
	/**
	 * Toma los datos del pojo y construye el objeto a devolver en response, con sus hipervinculos
	 * @param pojo
	 * @return
	 * @throws Excepcion 
	 */
	private ClienteDTO buildResponse(Cliente pojo) throws ClienteException {
		try {
			ClienteDTO clienteDTO = new ClienteDTO(pojo);
			
			 //Self link
			Link selfLink = WebMvcLinkBuilder.linkTo(ClienteController.class).slash(pojo.getDni()).withSelfRel();
			//Method link: Link al servicio que permitirá navegar hacia el domicilio relacionado al cliente
			Link domicilioLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DomicilioController.class).getById(pojo.getDomicilio().getId())).withRel("domicilio");
			
			clienteDTO.add(selfLink);
			clienteDTO.add(domicilioLink);
			
			return clienteDTO;
		} catch (Exception e) {
			throw new ClienteException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Toma los datos del error y construye el objeto a devolver
	 * @param result
	 * @return
	 */
	private ResponseEntity<Object> buildError(BindingResult result) {
		List<ErrorDTO> errorDtos = result.getFieldErrors().stream()
				.map(fieldError -> new ErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDtos);
	}
}
