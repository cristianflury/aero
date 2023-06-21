package com.daos.aero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daos.aero.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	Optional<Cliente> findByEmail(String email);
	List<Cliente> findByNombreOrApellido(String nombre, String apellido);
}
