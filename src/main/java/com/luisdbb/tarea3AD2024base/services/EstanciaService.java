package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	
	public List<Estancia> findByFechaBetweenAndParada(LocalDate fechaInicio, LocalDate fechaFin, Parada parada){
		return estanciaRepository.findByFechaBetweenAndParada(fechaInicio, fechaFin, parada);
	}
	
	public Estancia save(Estancia entity) {
		return estanciaRepository.save(entity);
	}
	
	public List<Estancia> findByPeregrino(Peregrino peregrino){
		return estanciaRepository.findByPeregrino(peregrino);
	}
	
	public boolean existsByPeregrinoAndParadaAndFecha(Peregrino peregrino, Parada parada, LocalDate fecha) {
		return estanciaRepository.existsByPeregrinoAndParadaAndFecha(peregrino,parada,fecha);
	}
}
