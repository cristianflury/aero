package com.daos.aero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daos.aero.model.Vuelo;

@Repository
public interface IVueloRepository extends JpaRepository<Vuelo, Long>{
	
	public List<Vuelo> findByTipoVuelo (String tipoVuelo);
	
}
