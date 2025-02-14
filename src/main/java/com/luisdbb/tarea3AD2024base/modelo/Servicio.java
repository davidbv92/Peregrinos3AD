package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;

/**
 * @author David Ballesteros
 * @since 14-02-2024
 */

public class Servicio {

	private Long id;
	private String nombre;
	private double precio;
	
	
	//constructores
	public Servicio() {
		
	}
	
	public Servicio(Long id, String nombre, double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}

	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	//métodos
}
