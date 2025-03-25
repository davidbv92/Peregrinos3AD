package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Clase que representa una parada en el camino de peregrinación.
 * Contiene información sobre la ubicación, responsable y relaciones con otras entidades
 * como {@link User}, {@link Visita} y {@link Estancia}.
 * 
 * @author David Ballesteros
 * @since 08-01-2025
 * @version 1.0
 */
@Entity
@Table(name = "Parada")
public class Parada implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Identificador único de la parada, generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	/**
     * Nombre descriptivo de la parada.
     */
	private String nombre;
	
	/**
     * Región donde se encuentra la parada (representada por un carácter).
     */
	private char region;
	
	/**
     * Nombre de la persona responsable de la parada.
     */
	private String responsable;
	
	/**
     * Usuario asociado a la gestión de esta parada (relación uno a uno).
     */
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private User usuario;
	
	/**
     * Lista de visitas registradas en esta parada (relación uno a muchos).
     */
	@OneToMany(mappedBy = "parada", cascade = CascadeType.ALL)
	private List<Visita> peregrinos=new ArrayList<Visita>();
	
	/**
     * Lista de estancias registradas en esta parada (relación uno a muchos).
     */
	@OneToMany(mappedBy = "parada")
	private List<Estancia> estancias=new ArrayList<Estancia>();
	
	//constructores
	/**
     * Constructor que inicializa una parada con todos sus atributos básicos.
     * 
     * @param id Identificador de la parada.
     * @param nombre Nombre de la parada.
     * @param region Región donde se ubica (como carácter).
     * @param responsable Nombre del responsable.
     */
	public Parada(Long id, String nombre, char region, String responsable, List<Peregrino> peregrinos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
//		this.peregrinos = peregrinos;
	}
	
	/**
     * Constructor alternativo que acepta el ID como entero.
     * 
     * @param id Identificador de la parada (convertido a Long internamente).
     * @param nombre Nombre de la parada.
     * @param region Región donde se ubica (como carácter).
     * @param responsable Nombre del responsable.
     */
	public Parada(Long id, String nombre, char region, String responsable) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	
	/**
     * Constructor alternativo que convierte la región de String a carácter.
     * 
     * @param id Identificador de la parada (convertido a Long internamente).
     * @param nombre Nombre de la parada.
     * @param region Región como String (se toma el primer carácter).
     * @param responsable Nombre del responsable.
     */
	public Parada(int id, String nombre, char region, String responsable) {
		super();
		this.id = (long) id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	
	/**
     * Constructor alternativo que convierte la región de String a carácter.
     * 
     * @param id Identificador de la parada (convertido a Long internamente).
     * @param nombre Nombre de la parada.
     * @param region Región como String (se toma el primer carácter).
     * @param responsable Nombre del responsable.
     */
	public Parada(int id, String nombre, String region, String responsable) {
		this.id=(long) id;
		this.nombre=nombre;
		this.region=region.charAt(0);
		this.responsable=responsable;
	}
	
	/**
     * Constructor por defecto sin parámetros (requerido por JPA).
     */
	public Parada() {
		
	}
	
	//getter y setter
	
	/**
     * Obtiene el ID de la parada.
     * 
     * @return Identificador único de la parada.
     */
	public Long getId() {
		return id;
	}
	
	/**
     * Establece el ID de la parada.
     * 
     * @param id Nuevo identificador.
     */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
     * Obtiene el nombre de la parada.
     * 
     * @return Nombre descriptivo de la parada.
     */
	public String getNombre() {
		return nombre;
	}
	
	/**
     * Establece el nombre de la parada.
     * 
     * @param nombre Nuevo nombre para la parada.
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
     * Obtiene la región donde se ubica la parada.
     * 
     * @return Carácter que representa la región.
     */
	public char getRegion() {
		return region;
	}
	
	/**
     * Establece la región de la parada.
     * 
     * @param region Nuevo carácter de región.
     */
	public void setRegion(char region) {
		this.region = region;
	}
	
	/**
     * Obtiene el nombre del responsable de la parada.
     * 
     * @return Nombre del responsable.
     */
	public String getResponsable() {
		return responsable;
	}
	
	/**
     * Establece el responsable de la parada.
     * 
     * @param responsable Nuevo nombre del responsable.
     */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	/**
     * Obtiene el usuario asociado a la parada.
     * 
     * @return Objeto {@link User} relacionado.
     */
	public User getUsuario() {
		return this.usuario;
	}
	
	/**
     * Establece el usuario asociado a la parada.
     * 
     * @param usuario Nuevo objeto {@link User}.
     */
	public void setUsuario(User usuario) {
		this.usuario=usuario;
	}
//	public List<Peregrino> getPeregrinos() {
//		return peregrinos;
//	}
//	public void setPeregrinos(List<Peregrino> peregrinos) {
//		this.peregrinos = peregrinos;
//	}
	
	
	//métodos
//	@Override
//	public int hashCode() {
//		return Objects.hash(id, nombre, peregrinos, region, responsable);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Parada other = (Parada) obj;
//		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
//				&& Objects.equals(peregrinos, other.peregrinos) && region == other.region
//				&& Objects.equals(responsable, other.responsable);
//	}
//	@Override
//	public String toString() {
//		return "Parada [id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable
//				+ ", peregrinos=" + peregrinos + "]";
//	}
}
