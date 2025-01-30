package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
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
	
	public List<Visita> findByPeregrino_Id(Long id) {
		return visitaRepository.findByPeregrino_Id(id);
	}

	public boolean existsByPeregrinoAndParadaAndFecha(Peregrino peregrino, Parada parada, LocalDate now) {
		return visitaRepository.existsByPeregrinoAndParadaAndFecha(peregrino,parada,now);
	}
}
