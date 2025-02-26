package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.User;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long>{

    List<Parada> findByNombre(String nombre);

    List<Parada> findByRegion(char region);

    List<Parada> findByResponsable(String responsable);
    
    Parada findByUsuario(User usuario);
}
