package com.daos.aero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daos.aero.model.Domicilio;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Long>{
	
}
