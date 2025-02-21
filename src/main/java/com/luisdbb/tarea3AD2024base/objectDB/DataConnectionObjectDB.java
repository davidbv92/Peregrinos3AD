package com.luisdbb.tarea3AD2024base.objectDB;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataConnectionObjectDB {

	private static DataConnectionObjectDB INSTANCE = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @Value("${objectDB.path}")
	   private String PATH;

    private DataConnectionObjectDB() {
    }


    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataConnectionObjectDB();
            INSTANCE.performConnection();
        }
    }


    public static EntityManager getInstance() {
        if (INSTANCE == null)
            createInstance();
        return em;
    }

    public void performConnection() {
        emf = Persistence.createEntityManagerFactory("objectdb:" + PATH); 
        em = emf.createEntityManager();
    }

    // Cerrar la conexión
    public void closeConnection() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
