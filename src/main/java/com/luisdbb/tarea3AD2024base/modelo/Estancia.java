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
 * Clase que representa una estancia de un peregrino en una parada específica.
 * Registra la fecha de la estancia y si es una parada VIP.
 * 
 * @author David Ballesteros
 * @since 08-01-2025
 * @version 1.0
 */
@Entity
@Table(name = "Estancia")
public class Estancia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Identificador único de la estancia, generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	/**
     * Fecha en la que ocurrió la estancia.
     */
	private LocalDate fecha;
	
	/**
     * Indica si la estancia fue en una parada VIP.
     */
	private boolean vip=false;
	
	/**
     * Peregrino asociado a esta estancia (relación muchos a uno).
     */
	@ManyToOne 
	@JoinColumn(name = "idPeregrino")
	private Peregrino peregrino;
	
	/**
     * Parada donde ocurrió la estancia (relación muchos a uno).
     */
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

	/**
     * Constructor que crea una estancia con ID, fecha y estado VIP.
     * 
     * @param id Identificador de la estancia (convertido a Long internamente).
     * @param fecha Fecha de la estancia.
     * @param vip Indica si es una parada VIP.
     */
	public Estancia(int id, LocalDate fecha, boolean vip) {
		this.id = (long) id;
		this.fecha = fecha;
		this.vip = vip;
	}
	
	/**
     * Constructor por defecto sin parámetros (requerido por JPA).
     */
	public Estancia() {
		
	}
	
	//getter y setter
	/**
     * Obtiene el ID de la estancia.
     * 
     * @return Identificador único de la estancia.
     */
	public Long getId() {
		return id;
	}

	/**
     * Establece el ID de la estancia.
     * 
     * @param id Nuevo identificador.
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Obtiene la fecha de la estancia.
     * 
     * @return Fecha de la estancia.
     */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
     * Establece la fecha de la estancia.
     * 
     * @param fecha Nueva fecha de estancia.
     */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
     * Verifica si la estancia es VIP.
     * 
     * @return true si es VIP, false en caso contrario.
     */
	public boolean isVip() {
		return vip;
	}

	/**
     * Establece el estado VIP de la estancia.
     * 
     * @param vip Nuevo estado VIP.
     */
	public void setVip(boolean vip) {
		this.vip = vip;
	}

	/**
     * Obtiene el peregrino asociado a la estancia.
     * 
     * @return Objeto {@link Peregrino} relacionado.
     */
	public Peregrino getPeregrino() {
		return peregrino;
	}

	/**
     * Establece el peregrino asociado a la estancia.
     * 
     * @param peregrino Nuevo objeto {@link Peregrino}.
     */
	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	/**
     * Obtiene la parada donde ocurrió la estancia.
     * 
     * @return Objeto {@link Parada} relacionado.
     */
	public Parada getParada() {
		return parada;
	}

	/**
     * Establece la parada donde ocurrió la estancia.
     * 
     * @param parada Nueva {@link Parada} asociada.
     */
	public void setParada(Parada parada) {
		this.parada = parada;
	}
	
	
	//métodos
}
