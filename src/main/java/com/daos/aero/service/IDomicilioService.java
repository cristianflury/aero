package com.daos.aero.service;

import java.util.List;

import com.daos.aero.model.Domicilio;

public interface IDomicilioService {
	
	void guardar(Domicilio domicilio);
	
	List<Domicilio> getAll();
	
	Domicilio getById(Long id);
}
