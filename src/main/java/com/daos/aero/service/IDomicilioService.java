package com.daos.aero.service;

import java.util.List;
import java.util.Optional;

import com.daos.aero.model.Domicilio;

public interface IDomicilioService {
	
	/**
	 * Actualiza los datos de un Domicilio
	 * @param domicilio
	 */
	void actualizar(Domicilio domicilio);
	
	/**
	 * Elimina un Domicilio
	 * @param domicilio
	 */
	void eliminar(Long id);
	
	/**
	 * Filtrar un domicilio por su número, calle y ciudad
	 * @param numero
	 * @param calle
	 * @param ciudad
	 * @return
	 */
	//Domicilio filtrar(String numero, String calle, String ciudad);
	
	/**
	 * Guarda un Domicilio
	 * @param domicilio
	 */
	Domicilio guardar(Domicilio domicilio);
	
	/**
	 * Devuelve lista completa de Domicilios
	 * @return
	 */
	List<Domicilio> getAll();
	
	/**
	 * Devuelve un Domicilio a partir de su identificador
	 * @param id
	 * @return
	 */
	Optional<Domicilio> getById(Long id);
}
