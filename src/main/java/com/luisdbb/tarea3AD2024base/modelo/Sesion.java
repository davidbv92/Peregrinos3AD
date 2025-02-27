package com.luisdbb.tarea3AD2024base.modelo;

/**
 * @author David Ballesteros
 * @since 08-01-2025
 */

public class Sesion {

	private static Sesion instancia;
    private Long id;
    private String nombre;
    private String tipo;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Sesion(Long id, String nombre, String tipo) {
        super();
        this.nombre = nombre;
        this.id = id;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Sesion [nombre=" + nombre + ", id=" + id + ", tipo=" + tipo + "]";
    }
}
