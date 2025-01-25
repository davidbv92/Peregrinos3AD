package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author David Ballesteros
 * @since 08-01-2024
 */

@Entity
@Table(name = "Estancia")
public class Estancia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private LocalDate fecha;
	
	private boolean vip=false;
	
	@ManyToOne 
	@JoinColumn(name = "idPeregrino")
	private Peregrino peregrino;
	
	@ManyToOne 
	@JoinColumn(name = "idParada")
	private Parada parada;
	
	//constructores
//	public Estancia(Long id, LocalDate fecha, boolean vip, Peregrino peregrino, Parada parada) {
//		super();
//		this.id = id;
//		this.fecha = fecha;
//		this.vip = vip;
//		this.peregrino = peregrino;
//		this.parada = parada;
//	}

	public Estancia(int id, LocalDate fecha, boolean vip) {
		this.id = (long) id;
		this.fecha = fecha;
		this.vip = vip;
	}
	public Estancia() {
		
	}
	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

//	public Peregrino getPeregrino() {
//		return peregrino;
//	}
//
//	public void setPeregrino(Peregrino peregrino) {
//		this.peregrino = peregrino;
//	}
//
//	public Parada getParada() {
//		return parada;
//	}
//
//	public void setParada(Parada parada) {
//		this.parada = parada;
//	}
	
	
	//métodos
}
