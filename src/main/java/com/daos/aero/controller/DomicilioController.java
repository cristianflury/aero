package com.daos.aero.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.dto.DomicilioDTO;
import com.daos.aero.excepcion.ClienteException;
import com.daos.aero.model.Domicilio;
import com.daos.aero.service.IDomicilioService;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
	@Autowired
	private IDomicilioService domicilioService;
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DomicilioDTO> getById(@PathVariable Long id) throws ClienteException
	{
		Optional<Domicilio> rta = domicilioService.getById(id);
		if(rta.isPresent()){
			Domicilio pojo = rta.get();
			return new ResponseEntity<DomicilioDTO>(buildResponse(pojo), HttpStatus.OK); //200 OK
		}
		
		throw new ClienteException("Domicilio no encontrado.", HttpStatus.NOT_FOUND); //404 Not Found
	}
	
	//Otros métodos
	/**
	 * Toma los datos del pojo y construye el objeto a devolver en response, con sus hipervinculos
	 * @param pojo
	 * @return
	 * @throws Excepcion 
	 */
	private DomicilioDTO buildResponse(Domicilio pojo) throws ClienteException {
		try {
			DomicilioDTO domicilioDTO = new DomicilioDTO(pojo);
			
			 //Self link
			Link selfLink = WebMvcLinkBuilder.linkTo(DomicilioController.class).slash(pojo.getId()).withSelfRel();
			
			domicilioDTO.add(selfLink);
			return domicilioDTO;
		} catch (Exception e) {
			throw new ClienteException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
