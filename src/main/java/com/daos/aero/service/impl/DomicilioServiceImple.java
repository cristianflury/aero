package com.daos.aero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.model.Domicilio;
import com.daos.aero.repository.IDomicilioRepository;
import com.daos.aero.service.IDomicilioService;

@Service
public class DomicilioServiceImple implements IDomicilioService{

	@Autowired
	private IDomicilioRepository repo;
	
	@Override
	public void actualizar(Domicilio domicilio) {
		repo.save(domicilio);
	}

	@Override
	public void eliminar(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public Domicilio guardar(Domicilio domicilio) {
		return repo.save(domicilio);
	}
	
	@Override
	public List<Domicilio> getAll() {
		return repo.findAll();
	}
	
	@Override
	public Optional<Domicilio> getById(Long id) {
		return repo.findById(id);
	}

	
}
