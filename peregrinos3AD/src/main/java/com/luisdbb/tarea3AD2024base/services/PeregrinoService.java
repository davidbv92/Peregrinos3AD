package com.luisdbb.tarea3AD2024base.services;

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
}
