package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ServicioRepository;

@Service
public class ServicioService {

	private ServicioRepository servicioRepository;
	
	@Autowired
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }
	
	public void save(Servicio servicio) {
		servicioRepository.save(servicio);
	}
	
	public void update(Servicio servicio) {
		servicioRepository.update(servicio);
	}

	public List<Servicio> findAll() {
		return servicioRepository.findAll();
	}
	
	public Servicio findById(Long id) {
		return servicioRepository.findById(id);
	}
	
	
	public Servicio findByName(String name) {
		return servicioRepository.findByName(name);
	}

	public Long calcularIdMaximo() {
		return servicioRepository.calcularIdMaximo();
	}

	public List<Servicio> findAllByParadaId(Long id) {
		return servicioRepository.findAllByParadaId(id);
	}
}
