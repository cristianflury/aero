package com.daos.aero.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.daos.aero.dto.PasajeRequestDTO;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.excepcion.Excepcion;
import com.daos.aero.model.Cliente;
import com.daos.aero.model.Pasaje;
import com.daos.aero.model.Vuelo;
import com.daos.aero.repository.PasajeRepository;
import com.daos.aero.service.IClienteService;
import com.daos.aero.service.PasajeService;
import com.daos.aero.service.VueloService;

@Service
public class PasajeServiceImpl implements PasajeService {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private VueloService vueloService;
	
	@Autowired
	private PasajeRepository pasajeRepository;

	@Override
	public PasajeResponseDTO emitir(PasajeRequestDTO requestDTO) throws Exception {
		
		Cliente cliente = validarCliente(requestDTO);
		Vuelo vuelo = validarVuelo(requestDTO);
		
		if ("INTERNACIONAL".equalsIgnoreCase(vuelo.getTipoVuelo())) {
			
			
		}
		
		return null;
	}


	private Cliente validarCliente(PasajeRequestDTO requestDTO) throws Excepcion {
		
        Cliente cliente = clienteService.getById(requestDTO.getDni());
		
		if (cliente == null) {
			
			throw new Excepcion("Cliente no encontrado", HttpStatus.NOT_FOUND.value());
			
		}
		
		return cliente;
		
	}
	
    private Vuelo validarVuelo(PasajeRequestDTO requestDTO) throws Excepcion {
		
        Vuelo vuelo = vueloService.getById(requestDTO.getNroVuelo()).orElse(null);
    	
    	if(vuelo == null) {

			throw new Excepcion("Vuelo no encontrado", HttpStatus.NOT_FOUND.value());
    		
    	}
    	 
    	
    	if(vuelo.getFecha().before(new Date())){
    		
    		throw new Excepcion("El vuelo elegido se encuentra fuera de fecha", HttpStatus.CONFLICT.value());
    	}
    	
    	
        Integer totalAsientos = vuelo.getNroFilas() * vuelo.getNroAsientosPorFila();
        
        if(requestDTO.getNroAsiento() <= totalAsientos) {
        	
        	List<Pasaje> pasajes = pasajeRepository.findByVuelo(vuelo);
        	
        	for (Pasaje pasaje : pasajes) {
        		
        		if (pasaje.getNroAsiento() == requestDTO.getNroAsiento()) {
        			
        			throw new Excepcion("Número de asiento no disponible", HttpStatus.CONFLICT.value());
        		}
        	}
        	
        } else {
        	
        	throw new Excepcion("Número de asiento no disponible", HttpStatus.CONFLICT.value());
        }
    		
     
    	return vuelo;
		
	}

}
