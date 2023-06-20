package com.daos.aero.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.daos.aero.dto.ClienteDTO;
import com.daos.aero.model.Cliente;
//import com.daos.aero.model.Domicilio;
//import com.daos.aero.model.Pasaporte;
import com.daos.aero.service.IClienteService;
//import com.daos.aero.service.IDomicilioService;
//import com.daos.aero.service.IPasaporteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private IClienteService clienteService;
	
	/**
	 * Busca un Cliente a partir de su DNI 
	 * Ej: curl --location --request GET 'http://localhost:8081/clientes/2'
	 * @param id dni
	 * @return Cliente encontrado o Not Found
	 * @throws Excepcion
	 */
	@GetMapping(value= "/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cliente> getById(@PathVariable Long dni) throws Exception{
		Optional<Cliente> rta = clienteService.getById(dni);
		if(rta.isPresent()) {
			Cliente pojo = rta.get();
			return new ResponseEntity<Cliente>(pojo, HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
