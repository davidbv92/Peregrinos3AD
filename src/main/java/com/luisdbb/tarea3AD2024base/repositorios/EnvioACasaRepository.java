package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.objectDB.DataConnectionObjectDB;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

@Repository
public class EnvioACasaRepository {

	private final DataConnectionObjectDB dataConnection;
	
	@Autowired
    public EnvioACasaRepository(DataConnectionObjectDB dataConnection) {
        this.dataConnection = dataConnection;
    }
	
	public void save(EnvioACasa envio) {
		
		EntityManager em=dataConnection.getEntityManager();
	    
	    try {
	        EntityTransaction et = em.getTransaction();

	        et.begin();
	        em.persist(envio);
	        et.commit();
	    } catch (Exception e) {
	        MiAlerta.showErrorAlert("Error al guardar el envío a casa", e.getMessage());
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	    } finally {
	    	dataConnection.closeConnection();
	    }
		
    }

	public List<EnvioACasa> findByIdParada(Long id) {
		List<EnvioACasa> listaEnvios = new ArrayList<>(0);
		EntityManager em=dataConnection.getEntityManager();
		try {
	        EntityTransaction et = em.getTransaction();

	        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada", EnvioACasa.class);
            query.setParameter("idParada", Sesion.getInstancia().getId());
            listaEnvios = query.getResultList();
	    } catch (Exception e) {
	        MiAlerta.showErrorAlert("Error al consultar los envíos a casa", e.getMessage());
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	    } finally {
	    	dataConnection.closeConnection();
	    }
		return listaEnvios;
	}
}
