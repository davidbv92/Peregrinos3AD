package com.luisdbb.tarea3AD2024base.objectDB;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataConnectionObjectDB {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Value("${objectDB.path}")
    private String path;

    public DataConnectionObjectDB() {
    }

    
    public void init() {
        try {
            //emf = Persistence.createEntityManagerFactory(path);
        	emf = Persistence.createEntityManagerFactory("peregrinosODB.odb");
            em = emf.createEntityManager();
            System.out.println("Init entro");
        } catch (Exception e) {
        	MiAlerta.showErrorAlert("Error al conectar con la base de datos ObjectDB", e.getMessage());
        }
    }

    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
        	//emf = Persistence.createEntityManagerFactory(path);
        	emf = Persistence.createEntityManagerFactory("peregrinosODB.odb");
            em = emf.createEntityManager();
        }
        return em;
    }

    
    public void closeConnection() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
