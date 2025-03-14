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

public class ExistDBManager {
    private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    static {
        try {
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        } catch (Exception e) {
            e.printStackTrace();
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
            } else {
                System.out.println("La colección ya existe: " + collectionName);
            }
            if (newCol != null) newCol.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
    
    public static void guardarArchivoEnExistDB(File archivo, String nombre) throws XMLDBException, IOException {
        Collection col = null;
        try {
            // Obtener la colección a la que se quiere agregar el archivo
            col = DatabaseManager.getCollection(URI +"/"+nombre, USER, PASSWORD); // Asegúrate de usar la colección correcta
            if (col == null) {
                System.out.println("La colección no existe.");
                return;
            }

            // Crear un recurso XML para guardar el archivo
            XMLResource xmlResource = (XMLResource) col.createResource(nombre, XMLResource.RESOURCE_TYPE);

            // Leer el archivo y almacenarlo como un recurso XML
//            try (FileInputStream fileInputStream = new FileInputStream(archivo)) {
//                xmlResource.setContent(fileInputStream);
//                col.storeResource(xmlResource);  // Guardar el archivo en la colección existente
//                System.out.println("El archivo ha sido guardado correctamente como: " + nombre);
//            }
            if (archivo.exists()) {
                XMLResource res = (XMLResource) col.createResource(archivo.getName(), "XMLResource");
                res.setContent(archivo);
                col.storeResource(res);
                System.out.println("Archivo almacenado en " + nombre + ": " + archivo.getName());
            } else {
                System.out.println("Archivo no encontrado: " + archivo.getName());
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
            throw e;  // Lanza la excepción para que el llamado pueda manejarla si es necesario
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
    
    public static List<String> obtenerArchivosDeColeccion(String collectionName) throws XMLDBException {
        List<String> archivos = new ArrayList<>();
        Collection col = null;
        try {
            // Obtener la colección
            col = DatabaseManager.getCollection(URI + collectionName, USER, PASSWORD);
            if (col == null) {
                System.out.println("La colección no existe.");
                return archivos;  // Retorna lista vacía si la colección no existe
            }

            // Obtener todos los recursos (archivos) dentro de la colección
            String[] recursos = col.listResources();  // Devuelve los nombres de todos los recursos

            // Añadir los nombres de los recursos a la lista de archivos
            for (String recurso : recursos) {
                archivos.add(recurso);  // Añadimos el nombre del recurso (archivo)
            }

            // Si no se encuentran archivos
            if (archivos.isEmpty()) {
                System.out.println("No se encontraron archivos en la colección: " + collectionName);
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
            throw e;  // Lanza la excepción para que el llamado pueda manejarla si es necesario
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }

        return archivos;  // Retorna la lista de nombres de archivos
    }
}
