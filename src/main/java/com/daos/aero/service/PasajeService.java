package com.daos.aero.service;


import com.daos.aero.dto.PasajeResponseDTO;

public interface PasajeService {
	
	public PasajeResponseDTO emitir(Long dni,Long nroVuelo, Integer nroAsiento ) throws Exception;
	
	

}
