package com.daos.aero.service;

import java.util.List;

import com.daos.aero.model.Cliente;

public interface IClienteService {
	
	void guardar(Cliente cliente);
	
	List<Cliente> getAll();
	
	Cliente getById(Long id);
}
