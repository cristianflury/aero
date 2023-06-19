package com.daos.aero.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.model.Cliente;
import com.daos.aero.model.Vuelo;
import com.daos.aero.service.IClienteService;
import com.daos.aero.service.VueloService;


@RestController
@RequestMapping("/pasaje")
public class PasajeController {
	
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private VueloService vueloService;
	
	
	@PostMapping
	public ResponseEntity<PasajeResponseDTO> emitirPasaje(@RequestBody PasajeRequestDTO requestBody) throws Exception {
		
	
		
		
	    return new ResponseEntity<PasajeResponseDTO>(new PasajeResponseDTO(), HttpStatus.OK);
		
	}

}
