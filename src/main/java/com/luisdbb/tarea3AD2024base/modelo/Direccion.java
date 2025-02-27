package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;


/**
 * @author David Ballesteros
 * @since 14-02-2025
 */

@Embeddable
public class Direccion {
	private Long idDireccion;
	private String direccion;
	private String localidad;
	
	//constructores
	public Direccion() {
		
	}
	public Direccion(Long id, String direccion, String localidad) {
		super();
		this.idDireccion = id;
		this.direccion = direccion;
		this.localidad = localidad;
	}

	//getters y setters
	
	public Long getId() {
		return idDireccion;
	}
	public void setId(Long id) {
		this.idDireccion = id;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	//métodos
	
}
