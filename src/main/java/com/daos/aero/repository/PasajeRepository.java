package com.daos.aero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daos.aero.model.Pasaje;
import com.daos.aero.model.Vuelo;

public interface PasajeRepository extends JpaRepository<Pasaje, Long>{
	
	List<Pasaje> findByVuelo(Vuelo vuelo);

}
