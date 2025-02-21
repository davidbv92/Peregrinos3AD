package com.luisdbb.tarea3AD2024base.modelo;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;

/**
 * @author David Ballesteros
 * @since 14-02-2024
 */

public class ConjuntoContratado {

	private Long id;
	private double precio;
	private char metodoPago;
	private String extra=null;
	
	private Long idEstancia;
	private Set<Long> servicios=new HashSet<Long>(0);
	
	
	//constructores
	public ConjuntoContratado(Long id, double precio, char metodoPago, String extra, Long idEstancia,
			Set<Long> servicios) {
		super();
		this.id = id;
		this.precio = precio;
		this.metodoPago = metodoPago;
		this.extra = extra;
		this.idEstancia = idEstancia;
		this.servicios = servicios;
	}
	
	public ConjuntoContratado(Long id, double precio, char metodoPago, Long idEstancia,
			Set<Long> servicios) {
		super();
		this.id = id;
		this.precio = precio;
		this.metodoPago = metodoPago;
		this.idEstancia = idEstancia;
		this.servicios = servicios;
	}
	
	public ConjuntoContratado() {
		
	}

	
	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public char getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(char metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Long getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(Long idEstancia) {
		this.idEstancia = idEstancia;
	}

	public Set<Long> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Long> servicios) {
		this.servicios = servicios;
	}
	
	//métodos
	
	
}
