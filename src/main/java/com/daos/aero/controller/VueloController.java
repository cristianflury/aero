package com.daos.aero.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daos.aero.dto.VueloDTO;
import com.daos.aero.excepcion.Excepcion;
import com.daos.aero.model.Vuelo;
import com.daos.aero.service.VueloService;

@RestController
@RequestMapping("/vuelos")
public class VueloController {

	@Autowired
	private VueloService service;

	/**
	 * Permite filtrar Vuelos
	 * Ej1 curl --location --request GET 'http://localhost:8081/vuelos?tipoVuelo=Comercial Lista los vuelos Comerciales
	 * Ej2 curl --location --request GET 'http://localhost:8081/vuelos Lista todos los Vuelos
	 * @param tipoVuelo
	 * @return
	 * @throws Excepcion
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VueloDTO> listarVuelos(@RequestParam(name = "tipoVuelo", required = false) String tipoVuelo)throws Excepcion 
	{

		List<Vuelo> vuelos = service.filtrar(tipoVuelo);
		List<VueloDTO> vudto = new ArrayList<VueloDTO>();
		for (Vuelo pojo : vuelos) {
			vudto.add(buildResponse(pojo));
		}
		return vudto;
	}

	/**
	 * Metodo que toma datos del pojo y construye objeto a devolver con hipervinculo
	 * @param pojo
	 * @return
	 * @throws Excepcion
	 */
	private VueloDTO buildResponse(Vuelo pojo) throws Excepcion 
	{
		try {
			VueloDTO dto = new VueloDTO(pojo);
			
			Link selfLink = WebMvcLinkBuilder.linkTo(VueloController.class).slash(pojo.getNro()).withSelfRel();
			dto.add(selfLink);
			return dto;
		} catch(Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}
	
	/**
	 * Busca un Vuelo a partir de su número de Vuelo 
	 * Ej: curl --location --request GET 'http://localhost:8081/vuelos/2'
	 * @param id nro de Vuelo
	 * @return Vuelo encontrado o Not Found
	 * @throws Excepcion
	 */
	@GetMapping(value= "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<VueloDTO> getById(@PathVariable Long id) throws Excepcion
	{
		Optional<Vuelo> rt = service.getById(id);
		if(rt.isPresent()) 
		{
			Vuelo pojo=rt.get();
			return new ResponseEntity<VueloDTO>(buildResponse(pojo), HttpStatus.OK);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
}
