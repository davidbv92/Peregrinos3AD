package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadaRepository paradaRepository;
	
	public Parada findByUsuario(User usuario) {
		return paradaRepository.findByUsuario(usuario);
	}

	public Parada find(Long id) {
		return paradaRepository.findById(id).get();
	}

	public Parada save(Parada entity) {
		return paradaRepository.save(entity);
	}
	
	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}
	
	public long getTotalParada() {
        return paradaRepository.count();
    }
}
