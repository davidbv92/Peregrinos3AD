package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * @author David Ballesteros
 * @since 08-01-2024
 */

@Entity
@Table(name = "Carnet")
public class Carnet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private LocalDate fechaexp;
	
	private double distancia;
	
	private int nvips;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private Peregrino peregrino;
	
	@ManyToOne 
	@JoinColumn(name = "idParada") 
	private Parada paradaInicial;
	
	
	//constructores
	public Carnet(Long id, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaexp = LocalDate.now();
		this.distancia = 0.0;
		this.nvips = 0;
		this.paradaInicial = paradaInicial;
	}
	
	

	public Carnet(Long id, LocalDate fechaexp, double distancia, int nvips, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaInicial = paradaInicial;
	}



	public Carnet(int id, LocalDate fechaExpedicion, double distancia, int nvips, Parada paradaInicial) {
		super();
		this.id = (long) id;
		this.fechaexp = fechaExpedicion;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaInicial = paradaInicial;
	}
	
	public Carnet() {
		
	}
	
	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaexp() {
		return fechaexp;
	}

	public void setFechaexp(LocalDate fechaexp) {
		this.fechaexp = fechaexp;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getNvips() {
		return nvips;
	}

	public void setNvips(int nvips) {
		this.nvips = nvips;
	}
	
	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParadaInicial() {
		return paradaInicial;
	}

	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}



	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaexp=" + fechaexp + ", distancia=" + distancia + ", nvips=" + nvips
				+ ", peregrino=" + peregrino.getId() + ", paradaInicial=" + paradaInicial + "]";
	}
	
	
	//métodos
	
}
