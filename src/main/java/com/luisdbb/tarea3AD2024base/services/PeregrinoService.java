package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	public Peregrino findByUsuario(User usuario) {
		return peregrinoRepository.findByUsuario(usuario);
	}
	
	public Peregrino save(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public Peregrino find(Long id) {
		return peregrinoRepository.findById(id).get();
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}

}
