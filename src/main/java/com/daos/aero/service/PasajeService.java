package com.daos.aero.service;


import java.util.Optional;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.excepcion.PasajeException;
import com.daos.aero.model.Pasaje;

public interface PasajeService {
	
	public PasajeResponseDTO emitir(Long dni,Long nroVuelo, Integer nroAsiento ) throws PasajeException;
	
	public Optional<Pasaje> consultar(Long nroPasaje);
	
	
}
