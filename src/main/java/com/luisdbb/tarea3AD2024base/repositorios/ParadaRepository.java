package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;

public interface ParadaRepository extends JpaRepository<Parada, Long>{

    List<Parada> findByNombre(String nombre);

    List<Parada> findByRegion(char region);

    List<Parada> findByResponsable(String responsable);
    
    Parada findByUsuario(User usuario);
}
