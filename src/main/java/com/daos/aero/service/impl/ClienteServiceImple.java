package com.daos.aero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.model.Cliente;
import com.daos.aero.repository.IClienteRepository;
import com.daos.aero.repository.IDomicilioRepository;
import com.daos.aero.service.IClienteService;

@Service
public class ClienteServiceImple implements IClienteService{

	@Autowired
	private IClienteRepository repo;
	@Autowired
	private IDomicilioRepository repoDom;

	@Override
	public void actualizar(Cliente cliente) {
		repoDom.save(cliente.getDomicilio());
		repo.save(cliente);
	}

	@Override
	public void eliminar(Long dni) {
		Optional<Cliente> rta = getById(dni);
		repo.deleteById(dni);
		repoDom.deleteById(rta.get().getDomicilio().getId());
	}

	
	@Override
	public Cliente guardar(Cliente cliente) {
		repoDom.save(cliente.getDomicilio());
		return repo.save(cliente);
	}
	
	@Override
	public Optional<Cliente> getById(Long id) {
		return repo.findById(id);
	}
	
	@Override
	public Optional<Cliente> getByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	@Override
	public Optional<Cliente> getByNumeroPasaporte(String numeroPasaporte) {
		return repo.findByNumeroPasaporte(numeroPasaporte);
	}

	@Override
	public List<Cliente> filtrar(String nombre, String apellido) {
		if(nombre == null && apellido == null) {
			return repo.findAll();
		}else {
			return repo.findByNombreOrApellido(nombre, apellido);
		}
	}
}
