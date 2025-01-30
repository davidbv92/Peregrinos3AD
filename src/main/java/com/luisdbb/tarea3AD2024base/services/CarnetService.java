package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class CarnetService {

	@Autowired
	private CarnetRepository carnetRepository;
	
	public Carnet findByPeregrino_Id(Long id) {
		return carnetRepository.findByPeregrino_Id(id);
	}
	
	public Carnet save(Carnet entity) {
		return carnetRepository.save(entity);
	}
}
