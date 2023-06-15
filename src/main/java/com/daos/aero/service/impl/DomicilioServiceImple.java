package com.daos.aero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.model.Domicilio;
import com.daos.aero.repository.IDomicilioRepository;
import com.daos.aero.service.IDomicilioService;

@Service
public class DomicilioServiceImple implements IDomicilioService{

	@Autowired
	private IDomicilioRepository domicilioRepository;
	
	@Override
	public void guardar(Domicilio domicilio) {
		domicilioRepository.save(domicilio);
		
	}

	@Override
	public List<Domicilio> getAll() {
		return domicilioRepository.findAll();
	}

	@Override
	public Domicilio getById(Long id) {
		return domicilioRepository.findById(id).orElse(null);
	}
}
