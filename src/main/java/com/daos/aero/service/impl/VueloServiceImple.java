package com.daos.aero.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.aero.excepcion.Excepcion;
import com.daos.aero.model.Vuelo;
import com.daos.aero.repository.IVueloRepository;
import com.daos.aero.service.VueloService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class VueloServiceImple implements VueloService {

	@Autowired
	private Validator validator;
	@Autowired
	private IVueloRepository repo;

	@Override
	public List<Vuelo> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Vuelo> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void actualiza(Vuelo v) {
		repo.save(v);
	}

	@Override
	public void inserta(Vuelo v) throws Exception {

		Set<ConstraintViolation<Vuelo>> cvu = validator.validate(v);
		if (cvu.size() > 0) {
			String err = "";
			for (ConstraintViolation<Vuelo> constraintViolation : cvu) {
				err += constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage() + "\n";
			}
			throw new Excepcion(err, 400);
		} else if (getById(v.getNro()).isPresent()) {
			throw new Excepcion("Ya existe una vuelo con ese número.", 400);
		} else
			repo.save(v);

	}

	@Override
	public void elimina(Long id) {
		repo.deleteById(id);

	}
	@Override
	public List<Vuelo> filtrar(String tipoVuelo){
		if(tipoVuelo==null)
			return repo.findAll();
		else
			return repo.findByTipoVuelo(tipoVuelo);
	}

}
