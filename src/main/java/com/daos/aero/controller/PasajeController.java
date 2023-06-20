package com.daos.aero.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.service.PasajeService;



@RestController
@RequestMapping("/pasaje")
public class PasajeController {
	
	
	@Autowired
	private PasajeService pasajeService;
	
	
	@PostMapping
	public ResponseEntity<PasajeResponseDTO> emitirPasaje(@RequestBody PasajeRequestDTO requestDTO) throws Exception {
		
	    PasajeResponseDTO pasaje = pasajeService.emitir(requestDTO.getDni(), requestDTO.getNroVuelo(), requestDTO.getNroAsiento()); 
		
		
	    return new ResponseEntity<PasajeResponseDTO>(new PasajeResponseDTO(), HttpStatus.OK);
		
	}

}
