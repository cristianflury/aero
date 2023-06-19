package com.daos.aero.service;

import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;

public interface PasajeService {
	
	public PasajeResponseDTO emitir(PasajeRequestDTO requestDTO) throws Exception;
	
	

}
