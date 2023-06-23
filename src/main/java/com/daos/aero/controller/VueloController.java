package com.daos.aero.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
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

import com.daos.aero.dto.VueloDTO;
import com.daos.aero.excepcion.Excepcion;
import com.daos.aero.excepcion.VueloException;
import com.daos.aero.model.Vuelo;
import com.daos.aero.service.VueloService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vuelos")
public class VueloController {

	@Autowired
	private VueloService service;

	/**
	 * Permite filtrar Vuelos Ej1 curl --location --request GET
	 * 'http://localhost:8081/vuelos?tipoVuelo=Comercial' Lista los vuelos
	 * Comerciales Ej2 curl --location --request GET 'http://localhost:8081/vuelos'
	 * Lista todos los Vuelos
	 * 
	 * @param tipoVuelo
	 * @return
	 * @throws Excepcion
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VueloDTO> listarVuelos(@RequestParam(name = "tipoVuelo", required = false) String tipoVuelo)
			throws VueloException {

		List<Vuelo> vuelos = service.filtrar(tipoVuelo);
		List<VueloDTO> vudto = new ArrayList<VueloDTO>();
		for (Vuelo pojo : vuelos) {
			vudto.add(buildResponse(pojo));
		}
		return vudto;
	}

	/**
	 * Metodo que toma datos del pojo y construye objeto a devolver con hipervinculo
	 * 
	 * @param pojo
	 * @return
	 * @throws VueloException
	 */
	private VueloDTO buildResponse(Vuelo pojo) throws VueloException {
		try {
			VueloDTO dto = new VueloDTO(pojo);

			Link selfLink = WebMvcLinkBuilder.linkTo(VueloController.class).slash(pojo.getNro()).withSelfRel();
			dto.add(selfLink);
			return dto;
		} catch (Exception e) {
			throw new VueloException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca un Vuelo a partir de su número de Vuelo Ej: curl --location --request
	 * GET 'http://localhost:8081/vuelos/2'
	 * 
	 * @param id nro de Vuelo
	 * @return Vuelo encontrado o Not Found
	 * @throws VueloException
	 */
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<VueloDTO> getById(@PathVariable Long id) throws VueloException {
		Optional<Vuelo> rt = service.getById(id);
		if (rt.isPresent()) {
			Vuelo pojo = rt.get();
			return new ResponseEntity<VueloDTO>(buildResponse(pojo), HttpStatus.OK);
		} else
			throw new VueloException("Vuelo no encontrado", HttpStatus.NOT_FOUND);
	}

	/**
	 * Insertar un nuevo Vuelo curl --location --request POST
	 * 'http://localhost:8081/vuelos' --header 'Accept: application/json' --header
	 * 'Content-Type: application/json' --data-raw '{ "nro": 3, "fecha":
	 * "2023-06-25", "destino": "Bariloche", "nroFilas": 15, "nroAsientosPorFila":
	 * 8, "tipoVuelo": "Comercial" }'
	 * 
	 * @param v Vuelo a insertar
	 * @return Vuelo insertado o error
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody VueloForm form, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
		}
		Vuelo v = form.toPojo();
		v.setOrigen("Sauce Viejo");
		v.setEstado("Registrado");

		service.inserta(v);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nro}").buildAndExpand(v.getNro())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	private String formatearError(BindingResult result) throws JsonProcessingException {
		List<Map<String, String>> errores = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		MensajeError err = new MensajeError();
		err.setCodigo("01");
		err.setMensajes(errores);

		// ahora usamos la librería Jackson
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(err);
		return json;

	}

	/**
	 * Modifica un Vuelo existente en base de datos: curl --location --request PUT
	 * 'http://localhost:8081/vuelos/3' --header 'Accept: application/json' --header
	 * 'Content-Type: application/json' --data-raw '{ "nro": 3, "fecha":
	 * "2023-06-30", }'
	 * 
	 * @param v Vuelo a Modificar
	 * @return Vuelo editado o error
	 * @throws VueloException
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> actualizar(@RequestBody VueloForm form, @PathVariable Long id)
			throws VueloException 
	{
		Optional<Vuelo> ra = service.getById(id);
		
		if (!ra.isPresent())
			throw new VueloException("Vuelo no encontrado", HttpStatus.NOT_FOUND);
		else {
			
			Vuelo v = form.toPojo();
			if(!v.getNro().equals(id)) {
				throw new VueloException("Numero de Vuelo no puede ser modificado", HttpStatus.BAD_REQUEST);
				} else {
			
			
			Date f = v.getFecha();
			
			service.actualizaFecha(f, id);
				}
			
			// con service.envio() se notifica a todos los pasajeros
			return ResponseEntity.ok(buildResponse(v));

		}
	}

	/**
	 * Pasa el Vuelo a estado Cancelado
	 * 		 curl --location --request DELETE 'http://localhost:8081/vuelos/3'
	 * @param nro Nro vuelo a cancelar
	 * @return 
	 * @throws VueloException
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@RequestBody VueloForm form, @PathVariable Long id) throws VueloException {
		if (!service.getById(id).isPresent())
			throw new VueloException("Vuelo no encontrado", HttpStatus.NOT_FOUND);
		else {
			Vuelo v = form.toPojo();
			v.setOrigen("Sauce Viejo");
			v.setEstado("Cancelado");

			if(!v.getNro().equals(id))
				throw new VueloException("Numero de Vuelo no puede ser modificado", HttpStatus.BAD_REQUEST);
			service.actualiza(v);
			// con service.envio() se notifica a todos los pasajeros
			return ResponseEntity.ok(buildResponse(v));
		}

	}

}
