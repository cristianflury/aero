package com.daos.aero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daos.aero.model.Pasaporte;

@Repository
public interface IPasaporteRepository extends JpaRepository<Pasaporte, Long>{

}
