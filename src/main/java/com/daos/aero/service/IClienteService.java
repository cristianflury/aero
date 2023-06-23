package com.daos.aero.service;

import java.util.List;
import java.util.Optional;

import com.daos.aero.model.Cliente;

public interface IClienteService {
	
	/**
	 * Actualiza los datos de un Cliente
	 * @param cliente
	 */
	void actualizar(Cliente cliente);
	
	/**
	 * Elimina un cliente
	 * @param cliente
	 */
	void eliminar(Long dni);
	
	/**
	 * Filtrar personas por nombre y/o apellido
	 * @param nombre
	 * @param apellido
	 * @return
	 */
	List<Cliente> filtrar(String nombre, String apellido);
	
	/**
	 * Guarda un Cliente
	 * @param cliente
	 */
	Cliente guardar(Cliente cliente);
	
	/**
	 * Devuelve un Cliente a partir de su DNI
	 * @param id
	 * @return
	 */
	Optional<Cliente> getById(Long id);
	
	/**
	 * Devuelve un Cliente a partir de su email
	 * @param email
	 * @return
	 */
	Optional<Cliente> getByEmail(String email);
}
