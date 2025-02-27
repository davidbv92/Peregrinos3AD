package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Ballesteros
 * @since 14-02-2025
 */

public class Servicio {

	private Long id;
	private String nombre;
	private double precio;
	private List<Long> paradas = new ArrayList<>(0);
	private List<Long> conjuntos = new ArrayList<>(0);
	
	
	//constructores
	public Servicio() {
		
	}
	
	public Servicio(Long id, String nombre, double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}
	

	public Servicio(Long id, String nombre, double precio, List<Long> paradas, List<Long> conjuntos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = paradas;
		this.conjuntos = conjuntos;
	}
	
	
	public Servicio(Long id, String nombre, double precio, List<Long> paradas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.paradas = paradas;
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

	public List<Long> getParadas() {
		return paradas;
	}

	public void setParadas(List<Long> paradas) {
		this.paradas = paradas;
	}

	public List<Long> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(List<Long> conjuntos) {
		this.conjuntos = conjuntos;
	}

	
	//métodos
	public boolean contieneParada(Long id) {
		return paradas.contains(id);
	}
	
	public String formatoCadena() {
		return getNombre()+" (+"+getPrecio()+"€)";
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", paradas=" + paradas
				+ ", conjuntos=" + conjuntos + "]";
	}
	
	
}
