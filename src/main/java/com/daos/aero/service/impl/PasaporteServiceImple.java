package com.daos.aero.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.model.Pasaporte;
import com.daos.aero.repository.IPasaporteRepository;
import com.daos.aero.service.IPasaporteService;

@Service
public class PasaporteServiceImple implements IPasaporteService{
	
	@Autowired
	private IPasaporteRepository pasaporteRepository;

	@Override
	public void guardar(Pasaporte pasaporte) {
		pasaporteRepository.save(pasaporte);
	}

	@Override
	public List<Pasaporte> getAll() {
		return pasaporteRepository.findAll();
	}

	@Override
	public Pasaporte getById(Long id) {
		return pasaporteRepository.findById(id).orElse(null);
	}
}
