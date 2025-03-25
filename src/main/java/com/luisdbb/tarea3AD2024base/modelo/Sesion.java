package com.luisdbb.tarea3AD2024base.modelo;

/**
 * Clase que implementa el patrón Singleton para manejar la sesión del usuario actual.
 * Almacena información básica del usuario logueado en el sistema.
 * 
 * @author David Ballesteros
 * @since 23-01-2025
 * @version 1.0
 */
public class Sesion {

	/**
     * Instancia única de la clase (parte del patrón Singleton).
     */
	private static Sesion instancia;
	
	/**
     * ID del usuario en sesión.
     */
    private Long id;
    
    /**
     * Nombre del usuario en sesión.
     */
    private String nombre;
    
    /**
     * Tipo de usuario.
     */
    private String tipo;

    
    /**
     * Constructor privado para prevenir instanciación directa (parte del patrón Singleton).
     */
    private Sesion() {
    }

    /**
     * Obtiene la instancia única de la sesión (implementación del patrón Singleton).
     * Si no existe una instancia, crea una nueva.
     * 
     * @return La única instancia permitida de Sesion.
     */
    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    /**
     * Constructor para crear una sesión con datos iniciales.
     * 
     * @param id Identificador del usuario.
     * @param nombre Nombre del usuario.
     * @param tipo Tipo de usuario (ej: "admin", "user").
     */
    public Sesion(Long id, String nombre, String tipo) {
        super();
        this.nombre = nombre;
        this.id = id;
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del usuario en sesión.
     * 
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario en sesión.
     * 
     * @param nombre Nuevo nombre de usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del usuario en sesión.
     * 
     * @return Identificador del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario en sesión.
     * 
     * @param id Nuevo identificador de usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de usuario en sesión.
     * 
     * @return Tipo de usuario (ej: "admin", "user").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de usuario en sesión.
     * 
     * @param tipo Nuevo tipo de usuario.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Representación en formato String del objeto Sesion.
     * 
     * @return Cadena con los valores de los atributos.
     */
    @Override
    public String toString() {
        return "Sesion [nombre=" + nombre + ", id=" + id + ", tipo=" + tipo + "]";
    }
}
