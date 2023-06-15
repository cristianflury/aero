package com.daos.aero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.model.Cliente;
import com.daos.aero.repository.IClienteRepository;
import com.daos.aero.service.IClienteService;

@Service
public class ClienteServiceImple implements IClienteService{

	@Autowired
	private IClienteRepository clienteRepository;
	
	@Override
	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);
		
	}

	@Override
	public List<Cliente> getAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente getById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

}
