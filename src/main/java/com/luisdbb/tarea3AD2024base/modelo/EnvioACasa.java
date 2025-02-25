package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * @author David Ballesteros
 * @since 14-02-2024
 */

@Entity
public class EnvioACasa extends Servicio{
	@Id
	private Long id;
	private double peso;
	private int[] volumen=new int[3];
	private boolean urgente=false;
	
	@Embedded
	private Direccion direccion;
	private Long idParada;
	
	
	//constructores
	public EnvioACasa(Long idServicio, String nombre,double precio,Long id, double peso, int[] volumen, boolean urgente, Direccion direccion, Long idParada) {
		super(idServicio,nombre,precio);
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
		this.idParada = idParada;
	}
	
	public EnvioACasa(Long idServicio, Long id, double peso, int[] volumen, boolean urgente, Direccion direccion, Long idParada) {
		super(idServicio,"Servicio a Casa" ,50.0);
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
		this.idParada = idParada;
	}
	
	public EnvioACasa() {
		
	}

	
	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}
	
	//métodos
	
}
