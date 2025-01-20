package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author David Ballesteros
 * @since 08-01-2024
 */

public class Sesion {

	@Id
	private Long id;
	
	private String nombre;
	
	private TipoSesion tipo;
	
	//constructores
	public Sesion() {
		
	}
	public Sesion(Long id, String nombre, TipoSesion tipo) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.tipo = tipo;
	}
	
	
	//getter y setter
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
	public TipoSesion getTipo() {
		return tipo;
	}
	public void setTipo(TipoSesion tipo) {
		this.tipo = tipo;
	}
	
	
	//métodos
	@Override
	public String toString() {
		return "Sesion [nombre=" + nombre + ", id=" + id + ", tipo=" + tipo + "]";
	}
}
