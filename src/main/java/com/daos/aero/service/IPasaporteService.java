package com.daos.aero.service;

import java.util.List;

import com.daos.aero.model.Pasaporte;

public interface IPasaporteService {
	
	void guardar(Pasaporte pasaporte);
	
	List<Pasaporte> getAll();
	
	Pasaporte getById(Long id);
}
