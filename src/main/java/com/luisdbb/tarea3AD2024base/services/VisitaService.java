package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;
import com.luisdbb.tarea3AD2024base.repositorios.VisitaRepository;

@Service
public class VisitaService {

	@Autowired
	private VisitaRepository visitaRepository;
	
	public Visita save(Visita entity) {
		return visitaRepository.save(entity);
	}
}
