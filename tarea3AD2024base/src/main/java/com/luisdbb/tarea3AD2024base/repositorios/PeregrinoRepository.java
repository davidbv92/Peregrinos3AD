package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;


@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {
	
    List<Peregrino> findByNombre(String nombre);

    List<Peregrino> findByNacionalidad(String nacionalidad);

    Peregrino findByCarnet_Id(Long carnetId);

    // Búsqueda personalizada por parada ID en visitas
    List<Peregrino> findByParadas_ParadaId(Long paradaId);

}
