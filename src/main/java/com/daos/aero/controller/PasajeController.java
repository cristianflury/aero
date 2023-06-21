package com.daos.aero.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daos.aero.dto.ErrorDTO;
import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.excepcion.PasajeException;
import com.daos.aero.model.Pasaje;
import com.daos.aero.service.PasajeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pasaje")
public class PasajeController {

	@Autowired
	private PasajeService pasajeService;

	@PostMapping
	public ResponseEntity<Object> emitirPasaje(@Valid @RequestBody PasajeRequestDTO requestDTO, BindingResult result) throws Exception {

		if (result.hasErrors()) {

			return buildError(result);
		}

		PasajeResponseDTO pasajeDTO = pasajeService.emitir(requestDTO.getDni(), requestDTO.getNroVuelo(), requestDTO.getNroAsiento());

		return new ResponseEntity<>(buildResponse(pasajeDTO), HttpStatus.CREATED);

	}

	@GetMapping(value = "/{nroPasaje}")
	public ResponseEntity<PasajeResponseDTO> getByID(@PathVariable Long nroPasaje) throws PasajeException {

		Optional<Pasaje> pasaje = pasajeService.consultar(nroPasaje);

		if (pasaje.isPresent()) {

			PasajeResponseDTO pasajeDTO = new PasajeResponseDTO(pasaje.get());

			return new ResponseEntity<PasajeResponseDTO>(buildResponse(pasajeDTO), HttpStatus.OK);

		} else {

			throw new PasajeException("Pasaje no encontrado", HttpStatus.NOT_FOUND);

		}

	}

	private PasajeResponseDTO buildResponse(PasajeResponseDTO pasajeDTO) throws PasajeException {
		try {

			Link selfLink = WebMvcLinkBuilder.
					linkTo(PasajeController.class).slash(pasajeDTO.getNumeroPasaje())
					.withSelfRel();
			
			Link clienteLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).getById(pasajeDTO.getDniCliente()))
					.withRel("cliente");

			Link vueloLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(VueloController.class).getById(pasajeDTO.getNroVuelo()))
					.withRel("vuelo");
			
			

			pasajeDTO.add(selfLink);
			pasajeDTO.add(clienteLink);
			pasajeDTO.add(vueloLink);

			return pasajeDTO;

		} catch (Exception e) {

			throw new PasajeException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<Object> buildError(BindingResult result) {

		List<ErrorDTO> errorDtos = result.getFieldErrors().stream()
				.map(fieldError -> new ErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDtos);
	}

}
