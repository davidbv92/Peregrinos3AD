package com.luisdbb.tarea3AD2024base.existDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;

public class ExistDBManager {
    private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ParadasPeregrinos5";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    static {
        try {
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        } catch (Exception e) {
        	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", e.getMessage());
        }
    }

    public static void createCollection(String collectionName) {
        Collection col = null;
        try {
            col = DatabaseManager.getCollection(URI, USER, PASSWORD);
            CollectionManagementService mgtService = 
                    (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
            
            Collection newCol = DatabaseManager.getCollection(URI + collectionName, USER, PASSWORD);
            if (newCol == null) {
                mgtService.createCollection(collectionName);
                System.out.println("Colección creada: " + collectionName);
            }
            if (newCol != null) newCol.close();
        } catch (Exception e) {
            MiAlerta.showErrorAlert("Error en el servidor de ExistDB", e.getMessage());
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", xe.getMessage());
                }
            }
        }
    }
    
    public static void guardarArchivoEnExistDB(File archivo, String nombre) throws XMLDBException, IOException {
        Collection col = null;
        try {
            col = DatabaseManager.getCollection(URI +"/"+nombre, USER, PASSWORD);
            if (col == null) {
                System.out.println("La colección no existe.");
                return;
            }
            XMLResource xmlResource = (XMLResource) col.createResource(nombre, XMLResource.RESOURCE_TYPE);

            if (archivo.exists()) {
                XMLResource res = (XMLResource) col.createResource(archivo.getName(), "XMLResource");
                res.setContent(archivo);
                col.storeResource(res);
                System.out.println("Archivo almacenado en " + nombre + ": " + archivo.getName());
            }
        } catch (XMLDBException e) {
        	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", e.getMessage());
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", xe.getMessage());
                }
            }
        }
    }
    
    public static List<String> obtenerArchivosDeColeccion(String collectionName) throws XMLDBException {
        List<String> archivos = new ArrayList<>();
        Collection col = null;
        try {
            col = DatabaseManager.getCollection(URI +"/"+ collectionName, USER, PASSWORD);
            if (col == null) {
                System.out.println("La colección no existe.");
                return archivos;
            }
            String[] recursos = col.listResources();
            for (String recurso : recursos) {
                archivos.add(recurso);
            }
            if (archivos.isEmpty()) {
                System.out.println("No se encontraron archivos en la colección: " + collectionName);
            }
        } catch (XMLDBException e) {
        	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", e.getMessage());
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                	MiAlerta.showErrorAlert("Error en el servidor de ExistDB", xe.getMessage());
                }
            }
        }
        return archivos;
    }
}
