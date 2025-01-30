package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;


public interface EstanciaRepository extends JpaRepository<Estancia, Long>{

    List<Estancia> findByFecha(LocalDate fecha);

    List<Estancia> findByFechaBetween(LocalDate startDate, LocalDate endDate);

    List<Estancia> findByPeregrino_Id(Long peregrinoId);
    
    List<Estancia> findByPeregrino(Peregrino peregrino);

    List<Estancia> findByParada_Id(Long paradaId);
    
    List<Estancia> findByParada(Parada parada);

    boolean existsByPeregrinoAndParadaAndFecha(Peregrino peregrino, Parada parada, LocalDate fecha);
    
    List<Estancia> findByFechaBetweenAndParada(LocalDate fechaInicio, LocalDate fechaFin, Parada parada);

}
