package com.daos.aero.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.excepcion.Excepcion;
import com.daos.aero.excepcion.PasajeException;
import com.daos.aero.model.Pasaje;
import com.daos.aero.service.PasajeService;



@RestController
@RequestMapping("/pasaje")
public class PasajeController {
	
	
	@Autowired
	private PasajeService pasajeService;
	
	
	@PostMapping
	public ResponseEntity<PasajeResponseDTO> emitirPasaje(@RequestBody PasajeRequestDTO requestDTO) throws Exception {
		
	    PasajeResponseDTO pasajeDTO = pasajeService.emitir(requestDTO.getDni(), requestDTO.getNroVuelo(), requestDTO.getNroAsiento()); 
		
		
	    return new ResponseEntity<PasajeResponseDTO>(buildResponse(pasajeDTO), HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/{nroPasaje}")
	public ResponseEntity<PasajeResponseDTO> getByID(@PathVariable Long nroPasaje) throws Exception {
		
		Optional<Pasaje> pasaje = pasajeService.consultar(nroPasaje);
		
		if (pasaje.isPresent()) {
			
			PasajeResponseDTO pasajeDTO = new PasajeResponseDTO(pasaje.get());
			
			return new ResponseEntity<PasajeResponseDTO>(buildResponse(pasajeDTO), HttpStatus.OK);
			
		} else {
			
			throw new PasajeException("Pasaje no encontrado", HttpStatus.NOT_FOUND);	
			
		}
		
	}
	
	
	private PasajeResponseDTO buildResponse(PasajeResponseDTO pasajeDTO) throws Excepcion {
		try {
			
			
			Link selfLink = WebMvcLinkBuilder.linkTo(PasajeController.class)
										.slash(pasajeDTO.getNumeroPasaje())                
										.withSelfRel();
			
			
			Link vueloLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VueloController.class)
			       													.getById(pasajeDTO.getNroVuelo()))
			       													.withRel("vuelo");
			
			pasajeDTO.add(selfLink);
			pasajeDTO.add(vueloLink);
			
			return pasajeDTO;
			
			
		} catch (Exception e) {
			
			throw new Excepcion(e.getMessage(), 500);
		}
	}

}
