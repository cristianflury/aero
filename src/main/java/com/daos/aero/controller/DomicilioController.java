package com.daos.aero.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.model.Domicilio;
import com.daos.aero.service.IDomicilioService;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
	@Autowired
	private IDomicilioService domicilioService;
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Domicilio> getById(@PathVariable Long id) throws Exception
	{
		Optional<Domicilio> rta = domicilioService.getById(id);
		if(rta.isPresent()){
			Domicilio pojo = rta.get();
			return new ResponseEntity<Domicilio>(pojo, HttpStatus.OK); //200 OK
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found
	}
}
