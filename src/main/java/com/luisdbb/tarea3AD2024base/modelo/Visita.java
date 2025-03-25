package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa una visita realizada por un peregrino a una parada específica.
 * Esta entidad registra el momento en que un peregrino visita una parada
 * en su camino de peregrinación.
 * 
 * @author David Ballesteros
 * @since 08-01-2025
 * @version 1.0
 */
@Entity
@Table(name = "Visita")
public class Visita {

	/**
     * Identificador único de la visita (generado automáticamente)
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Peregrino que realiza la visita (relación ManyToOne)
     */
	@ManyToOne
	@JoinColumn(name = "peregrino_id")
	private Peregrino peregrino;
	
	/**
     * Parada que es visitada (relación ManyToOne)
     */
	@ManyToOne
	@JoinColumn(name = "parada_id")
	private Parada parada;
	
	/**
     * Fecha en que se realizó la visita
     */
	private LocalDate fecha;

	/**
     * Obtiene el ID de la visita
     * @return el identificador único de la visita
     */
	public Long getId() {
		return id;
	}

	/**
     * Establece el ID de la visita
     * @param id el nuevo identificador único
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Obtiene el peregrino que realizó la visita
     * @return el objeto {@link Peregrino} asociado
     */
	public Peregrino getPeregrino() {
		return peregrino;
	}

	/**
     * Establece el peregrino que realizó la visita
     * @param peregrino el nuevo objeto {@link Peregrino} asociado
     */
	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	/**
     * Obtiene la parada visitada
     * @return el objeto {@link Parada} asociado
     */
	public Parada getParada() {
		return parada;
	}

	/**
     * Establece la parada visitada
     * @param parada el nuevo objeto {@link Parada} asociado
     */
	public void setParada(Parada parada) {
		this.parada = parada;
	}

	/**
     * Obtiene la fecha de la visita
     * @return la fecha en que se realizó la visita
     */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
     * Establece la fecha de la visita
     * @param fecha la nueva fecha de la visita
     */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
}
