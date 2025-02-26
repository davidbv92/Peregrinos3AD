package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long>{

    List<Carnet> findByParadaInicial_Id(Long paradaInicialId);
    
    Carnet findByPeregrino_Id(Long peregrinoId);
    
    Carnet findFirstByOrderByDistanciaDesc();
}
