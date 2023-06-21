package com.daos.aero.controller;

import java.util.ArrayList;
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
	 * curl --location --request GET 'http://localhost:8080/clientes/43644112' \
	 * --header 'Content-Type: application/json' \
	 * --data-raw '{
	 *     "dni":"43644112",
	 *     "nombre":"gian",
	 *     "apellido":"mehring",
	 *     "email":"gian@mehring.com",
	 *     "nacimiento":"2001-07-20",
	 *     "domicilio":{
	 *         "id":1
	 *     }
	 * }'
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
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found
	}
	
	/**
	 * Filtra todos los clientes y también por nombre y/o apellido. 
	 * Ej1 curl --location --request GET 'http://localhost:8080/clientes?nombre=gian&apellido=mehring'
	 * Ej2 curl --location --request GET 'http://localhost:8080/clientes?nombre=gian'
	 * Ej3 curl --location --request GET 'http://localhost:8080/clientes'
	 * curl --location --request GET 'http://localhost:8080/clientes?nombre=gian&apellido=null' \
	 * --header 'Content-Type: application/json' \
	 * --data-raw '{
	 *     "dni":"43644112",
	 *     "nombre":"gian",
	 *     "apellido":"mehring",
	 *     "email":"gian@mehring.com",
	 *     "nacimiento":"2001-07-20",
	 *     "domicilio":{
	 *         "id":1
	 *     }
	 * }'
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
	 * curl --location 'http://localhost:8080/clientes' \
	 * --header 'Content-Type: application/json' \
	 * --data '{
	 *     "dni":"43644112",
	 *     "nombre":"gian",
	 *     "apellido":"mmmmmme",
	 *     "email":"gian@mehring.com",
	 *     "nacimiento":"2001-07-20",
	 *     "domicilio":{
	 *         "calle": "Mercedita",
	 *         "numero": "1519",
	 *         "ciudad": "Humbol"
	 *     }
	 * }'
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
		
		if(rtaCliente.isPresent() && rtaCliente.get().getDni().equals(cliente.getDni())) { //DNI es el identificador. No se puede repetir
			throw new ClienteException("El DNI indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El DNI indicado ya se encuentra en la base de datos"); //404 Not Found
		}
		
		rtaCliente = clienteService.getByEmail(cliente.getEmail());
		if(rtaCliente.isPresent() && rtaCliente.get().getEmail().equals(cliente.getEmail())) { //email es único. No se puede repetir.
			throw new ClienteException("El email indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El email indicado ya se encuentra en la base de datos"); //404 Not Found
		}
		
		//guardo el cliente
		clienteService.guardar(cliente);
		
		//URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/clientes/{dni}").buildAndExpand(cliente.getDni()).toUri(); //Por convención en REST, se devuelve la  url del recurso recién creado
		//falta poner la URL del domicilio
		return ResponseEntity.ok(buildResponse(cliente)); //200 OK
		//return ResponseEntity.created(location).build(); //201 (Recurso creado correctamente)
	}
	
	/**
	 * Modifica un cliente y domicilio existente en la base de datos
	 * curl --location --request PUT 'http://localhost:8080/clientes/43644112' \
	 * --header 'Content-Type: application/json' \
	 * --data-raw '{
	 *     "dni":"43644112",
	 *     "nombre":"gian",
	 *     "apellido":"mehring",
	 *     "email":"gian@mehring.com",
	 *     "nacimiento":"2001-07-20",
	 *     "domicilio":{
	 *         "id":1,
	 *         "numero": "1519",
	 *         "calle": "Merceditas",
	 *         "ciudad": "Humboldt"
	 *     },
	 *     "numeroPasaporte":"1",
	 *     "vencimientoPasaporte":"2030-01-01"
	 * }'
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
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el cliente que quiere actualizar."); //404 Not Found
		}else {
			Cliente cliente = dto.toPojo();
			if(!cliente.getDni().equals(dni)){//DNI es el identificador. No se puede modificar.
				throw new ClienteException("No puede modificar el DNI.", HttpStatus.NOT_FOUND); //404 Not Found
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No puede modificar el DNI."); //404 Not Found
			}
			
			Optional<Cliente> rta2 = clienteService.getByEmail(cliente.getEmail());
			if(!rta.equals(rta2)) {
				if(rta2.isPresent() && rta2.get().getEmail().equals(cliente.getEmail())){ //email es único. No se puede repetir.
					throw new ClienteException("El email indicado ya se encuentra en la base de datos", HttpStatus.NOT_FOUND); //404 Not Found
//					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El email indicado ya se encuentra en la base de datos"); //404 Not Found
				}
			}
			
			//actualizo el cliente
			clienteService.actualizar(cliente);
			
			return ResponseEntity.ok(buildResponse(cliente)); //200 OK
		}
	}
	
	
	/**
	 * Elimina un cliente existente en la base de datos.
	 * curl --location --request DELETE 'http://localhost:8080/clientes/43644112' \
	 * --data ''
	 * @param dni
	 * @return
	 */
	@DeleteMapping("/{dni}")
	public ResponseEntity<String> eliminar(@PathVariable Long dni) throws ClienteException{
		if(!clienteService.getById(dni).isPresent()) {
			throw new ClienteException("No existe un cliente con ese DNI.", HttpStatus.NOT_FOUND); //404 Not Found
//			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un cliente con ese DNI."); //404 Not Found
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
