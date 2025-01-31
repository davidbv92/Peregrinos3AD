package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

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
	
	public User getUserWithCarnetMaxDistance() {
        Carnet carnet = carnetRepository.findFirstByOrderByDistanciaDesc();
        if(carnet!=null) {
        	return carnet.getPeregrino().getUsuario();
        }else {
        	return null;
        }
    }
}
