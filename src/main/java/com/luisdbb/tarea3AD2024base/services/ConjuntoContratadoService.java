package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoContratadoRepository;

@Service
public class ConjuntoContratadoService {

	private ConjuntoContratadoRepository conjuntoContratadoRepository;
	
	@Autowired
    public ConjuntoContratadoService(ConjuntoContratadoRepository conjuntoContratadoRepository) {
        this.conjuntoContratadoRepository = conjuntoContratadoRepository;
    }
	
	public void save(ConjuntoContratado conjunto) {
		conjuntoContratadoRepository.save(conjunto);
	}
	
	public List<ConjuntoContratado> findAll() {
		return conjuntoContratadoRepository.findAll();
	}
	
	public Long calcularIdMaximo() {
		return conjuntoContratadoRepository.calcularIdMaximo();
	}
	
}
