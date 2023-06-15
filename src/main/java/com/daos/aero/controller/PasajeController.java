package com.daos.aero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;


@RestController
@RequestMapping("/pasaje")
public class PasajeController {
	
	
	@PostMapping
	public ResponseEntity<PasajeResponseDTO> emitirPasaje( @RequestBody PasajeRequestDTO requestBody) {
		
		
		return null;
		
		
	}

}
