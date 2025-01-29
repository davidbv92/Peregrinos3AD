package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
<<<<<<< HEAD
=======
import com.luisdbb.tarea3AD2024base.modelo.Parada;
>>>>>>> 2a9e288fa9e52d479f8307f125b075f77c9a2b91

public interface EstanciaRepository extends JpaRepository<Estancia, Long>{

    List<Estancia> findByFecha(LocalDate fecha);

    List<Estancia> findByFechaBetween(LocalDate startDate, LocalDate endDate);

    List<Estancia> findByPeregrino_Id(Long peregrinoId);

    List<Estancia> findByParada_Id(Long paradaId);
<<<<<<< HEAD
=======
    
    List<Estancia> findByFechaBetweenAndParada(LocalDate fechaInicio, LocalDate fechaFin, Parada parada);
>>>>>>> 2a9e288fa9e52d479f8307f125b075f77c9a2b91
}
