package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Visita;

public interface VisitaRepository extends JpaRepository<Visita, Long>{

    List<Visita> findByPeregrino_Id(Long peregrinoId);

    List<Visita> findByParada_Id(Long paradaId);

    List<Visita> findByFecha(LocalDate fecha);

    List<Visita> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
