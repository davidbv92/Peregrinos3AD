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
import jakarta.persistence.Table;

/**
 * Clase que representa un carnet de peregrino en el sistema.
 * Contiene información sobre la expedición, distancia recorrida, 
 * paradas VIP y relaciones con otras entidades como {@link Peregrino} y {@link Parada}.
 * 
 * @author David Ballesteros
 * @since 08-01-2025
 * @version 1.0
 */
@Entity
@Table(name = "Carnet")
public class Carnet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
     * Identificador único del carnet, generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	/**
     * Fecha de expedición del carnet.
     */
	private LocalDate fechaexp;
	
	/**
     * Distancia total recorrida por el peregrino (en kilómetros).
     */
	private double distancia;
	
	/**
     * Número de estancias VIP realizadas por el peregrino.
     */
	private int nvips;
	
	/**
     * Peregrino asociado a este carnet (relación uno a uno).
     */
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private Peregrino peregrino;
	
	/**
     * Parada inicial donde se expidió el carnet (relación muchos a uno).
     */
	@ManyToOne 
	@JoinColumn(name = "idParada") 
	private Parada paradaInicial;
	
	
	//constructores
	/**
     * Constructor básico que inicializa un carnet con valores por defecto.
     * 
     * @param id Identificador del carnet.
     * @param paradaInicial Parada donde se expide el carnet.
     */
	public Carnet(Long id, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaexp = LocalDate.now();
		this.distancia = 0.0;
		this.nvips = 0;
		this.paradaInicial = paradaInicial;
	}
	
	
	/**
     * Constructor completo para crear un carnet con todos los atributos.
     * 
     * @param id Identificador del carnet.
     * @param fechaexp Fecha de expedición.
     * @param distancia Distancia recorrida.
     * @param nvips Número de paradas VIP visitadas.
     * @param paradaInicial Parada inicial asociada.
     */
	public Carnet(Long id, LocalDate fechaexp, double distancia, int nvips, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaInicial = paradaInicial;
	}


	/**
     * Constructor alternativo que acepta el ID como entero.
     * 
     * @param id Identificador del carnet (convertido a Long internamente).
     * @param fechaExpedicion Fecha de expedición.
     * @param distancia Distancia recorrida.
     * @param nvips Número de paradas VIP visitadas.
     * @param paradaInicial Parada inicial asociada.
     */
	public Carnet(int id, LocalDate fechaExpedicion, double distancia, int nvips, Parada paradaInicial) {
		super();
		this.id = (long) id;
		this.fechaexp = fechaExpedicion;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaInicial = paradaInicial;
	}
	
	/**
     * Constructor por defecto sin parámetros (requerido por JPA).
     */
	public Carnet() {
		
	}
	
	
	//getter y setter
	/**
     * Obtiene el ID del carnet.
     * 
     * @return Identificador único del carnet.
     */
	public Long getId() {
		return id;
	}

	/**
     * Establece el ID del carnet.
     * 
     * @param id Nuevo identificador.
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Obtiene la fecha de expedición del carnet.
     * 
     * @return Fecha de expedición.
     */
	public LocalDate getFechaexp() {
		return fechaexp;
	}

	/**
     * Establece la fecha de expedición del carnet.
     * 
     * @param fechaexp Nueva fecha de expedición.
     */
	public void setFechaexp(LocalDate fechaexp) {
		this.fechaexp = fechaexp;
	}

	/**
     * Obtiene la distancia recorrida por el peregrino.
     * 
     * @return Distancia en kilómetros.
     */
	public double getDistancia() {
		return distancia;
	}

	/**
     * Establece la distancia recorrida por el peregrino.
     * 
     * @param distancia Nueva distancia en kilómetros.
     */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	/**
     * Obtiene el número de paradas VIP visitadas.
     * 
     * @return Cantidad de paradas VIP.
     */
	public int getNvips() {
		return nvips;
	}

	/**
     * Establece el número de paradas VIP visitadas.
     * 
     * @param nvips Nueva cantidad de paradas VIP.
     */
	public void setNvips(int nvips) {
		this.nvips = nvips;
	}
	
	/**
     * Obtiene el peregrino asociado al carnet.
     * 
     * @return Objeto {@link Peregrino} relacionado.
     */
	public Peregrino getPeregrino() {
		return peregrino;
	}

	/**
     * Establece el peregrino asociado al carnet.
     * 
     * @param peregrino Nuevo objeto {@link Peregrino}.
     */
	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	/**
     * Obtiene la parada inicial del carnet.
     * 
     * @return Objeto {@link Parada} asociado.
     */
	public Parada getParadaInicial() {
		return paradaInicial;
	}

	/**
     * Establece la parada inicial del carnet.
     * 
     * @param paradaInicial Nueva {@link Parada} inicial.
     */
	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}


	//métodos

	 /**
     * Representación en formato String del objeto Carnet.
     * 
     * @return Cadena con los valores de los atributos principales.
     */
	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaexp=" + fechaexp + ", distancia=" + distancia + ", nvips=" + nvips
				+ ", peregrino=" + peregrino.getId() + ", paradaInicial=" + paradaInicial + "]";
	}
	
	
	
	
}
