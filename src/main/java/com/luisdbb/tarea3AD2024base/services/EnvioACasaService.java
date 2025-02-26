package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioACasaRepository;

@Service
public class EnvioACasaService {

	private EnvioACasaRepository envioACasaRepository;
	
	@Autowired
    public EnvioACasaService(EnvioACasaRepository envioACasaRepository) {
        this.envioACasaRepository = envioACasaRepository;
    }
	
	public void save(EnvioACasa envio) {
		envioACasaRepository.save(envio);
	}

	public List<EnvioACasa> findByIdParada(Long id) {
		return envioACasaRepository.findByIdParada(id);
		
	}
}
