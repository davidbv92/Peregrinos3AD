package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Visita;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long>{

    List<Visita> findByPeregrino_Id(Long peregrinoId);

    List<Visita> findByParada_Id(Long paradaId);

    List<Visita> findByFecha(LocalDate fecha);

    List<Visita> findByFechaBetween(LocalDate startDate, LocalDate endDate);

	boolean existsByPeregrinoAndParadaAndFecha(Peregrino peregrino, Parada parada, LocalDate now);
	
}
