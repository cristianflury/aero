package com.daos.aero.service;

import java.util.List;
import java.util.Optional;

import com.daos.aero.model.Vuelo;


public interface VueloService {
	
	/**
	 * Devuelve lista completa de Vuelos
	 * @return Lista de Vuelos
	 */
	public List<Vuelo> getAll();
	/**
	 * Obtiene Vuelo a partir de su identificador
	 * @param id
	 * @return
	 */
	public Optional<Vuelo> getById(Long id);
	
	/**
	 * Actualiza datos de un Vuelo
	 * @param v
	 */
	public void actualiza(Vuelo v);
	
	/**
	 * Inserta un nuevo Vuelo
	 * @param v
	 * @throws Exception
	 */
	public void inserta(Vuelo v) throws Exception;
	
	/**
	 * Elimina un Vuelo
	 * @param id nro de vuelo a eliminar
	 */
	public void elimina(Long id);
	
	public List<Vuelo> filtrar(String tipoVuelo);

}	
