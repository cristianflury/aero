package com.daos.aero.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.daos.aero.dto.PasajeResponseDTO;
import com.daos.aero.excepcion.PasajeException;
import com.daos.aero.model.Cliente;
import com.daos.aero.model.Pasaje;
import com.daos.aero.model.Vuelo;
import com.daos.aero.repository.PasajeRepository;
import com.daos.aero.service.IClienteService;
import com.daos.aero.service.PasajeService;
import com.daos.aero.service.VueloService;

@Service
public class PasajeServiceImpl implements PasajeService {

	private static final String TIPO_VUELO_INTERNACIONAL = "INTERNACIONAL";

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private VueloService vueloService;

	@Autowired
	private PasajeRepository pasajeRepository;

	@Override
	public PasajeResponseDTO emitir(Long dni, Long nroVuelo, Integer nroAsiento) throws PasajeException {

		Cliente cliente = validarCliente(dni);

		Vuelo vuelo = validarVuelo(nroVuelo, nroAsiento);

		if (TIPO_VUELO_INTERNACIONAL.equalsIgnoreCase(vuelo.getTipoVuelo())) {

			validarPasaporte(cliente);
		}

		Double importe = getCostoMock();

		Pasaje pasaje = registrarPasaje(nroAsiento, cliente, vuelo, importe);

		return new PasajeResponseDTO(pasaje);
		
	}
	
	@Override
	public Optional<Pasaje> consultar(Long nroPasaje) {
		
		return pasajeRepository.findById(nroPasaje);
	}

	private Cliente validarCliente(Long dni) throws PasajeException {

		Cliente cliente = clienteService.getById(dni).orElse(null);

		if (cliente == null) {

			throw new PasajeException("Cliente no encontrado", HttpStatus.NOT_FOUND);

		}

		return cliente;

	}

	private Vuelo validarVuelo(Long nroVuelo, Integer nroAsiento) throws PasajeException {

		Vuelo vuelo = vueloService.getById(nroVuelo).orElse(null);

		if (vuelo == null) {

			throw new PasajeException("Vuelo no encontrado", HttpStatus.NOT_FOUND);

		}

		if (vuelo.getFecha().before(new Date())) {

			throw new PasajeException("El vuelo elegido se encuentra fuera de fecha", HttpStatus.CONFLICT);
		}

		validarAsiento(nroAsiento, vuelo);

		return vuelo;

	}

	private void validarAsiento(Integer nroAsiento, Vuelo vuelo) throws PasajeException {
		
		Integer totalAsientos = vuelo.getNroFilas() * vuelo.getNroAsientosPorFila();

		if (nroAsiento <= totalAsientos) {

			List<Pasaje> pasajes = pasajeRepository.findByVuelo(vuelo);
			
			if(!pasajes.isEmpty()) {
				
				for (Pasaje pasaje : pasajes) {

					if (pasaje.getNroAsiento() == nroAsiento) {

						throw new PasajeException("Número de asiento no disponible", HttpStatus.CONFLICT);
					}
				}
			}

			

		} else {

			throw new PasajeException("Número de asiento no disponible", HttpStatus.CONFLICT);
		}
	}

	private void validarPasaporte(Cliente cliente) throws PasajeException {

		if (cliente.getNumeroPasaporte() == null || cliente.getVencimientoPasaporte().before(new Date())) {

			throw new PasajeException("El cliente no posee pasaporte o el mismo se encuentra vencido", HttpStatus.CONFLICT);
		}
	}

	private Double getCostoMock() {

		return 25000.00;
	}

	private Pasaje registrarPasaje(Integer nroAsiento, Cliente cliente, Vuelo vuelo, Double importe) {

		Pasaje pasaje = new Pasaje();

		pasaje.setCliente(cliente);
		pasaje.setVuelo(vuelo);
		pasaje.setNroAsiento(nroAsiento);
		pasaje.setImporte(importe);

		return pasajeRepository.save(pasaje);
	}

	
	

}
