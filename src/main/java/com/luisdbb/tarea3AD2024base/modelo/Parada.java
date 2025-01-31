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
 * @author David Ballesteros
 * @since 08-01-2024
 */

@Entity
@Table(name = "Parada")
public class Parada implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String nombre;
	
	private char region;
	
	private String responsable;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private User usuario;
	
	@OneToMany(mappedBy = "parada", cascade = CascadeType.ALL)
	private List<Visita> peregrinos=new ArrayList<Visita>();
	
	@OneToMany(mappedBy = "parada")
	private List<Estancia> estancias=new ArrayList<Estancia>();
	
	//constructores
	public Parada(Long id, String nombre, char region, String responsable, List<Peregrino> peregrinos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
//		this.peregrinos = peregrinos;
	}
	
	public Parada(Long id, String nombre, char region, String responsable) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	
	public Parada(int id, String nombre, char region, String responsable) {
		super();
		this.id = (long) id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}
	public Parada(int id, String nombre, String region, String responsable) {
		this.id=(long) id;
		this.nombre=nombre;
		this.region=region.charAt(0);
		this.responsable=responsable;
	}
	
	public Parada() {
		
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
	public char getRegion() {
		return region;
	}
	public void setRegion(char region) {
		this.region = region;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	public User getUsuario() {
		return this.usuario;
	}
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
