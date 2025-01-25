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
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * @author David Ballesteros
 * @since 08-01-2024
 */

@Entity
@Table(name = "Peregrino")
public class Peregrino implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String nombre;
	
	private String nacionalidad;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private User usuario;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private Carnet carnet;
	
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<Visita> paradas=new ArrayList<Visita>();
	
	@OneToMany(mappedBy = "peregrino")
	private List<Estancia> estancias=new ArrayList<Estancia>();
	
	//constructores
//	public Peregrino(Long id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial,List<Parada> paradas,List<Estancia> estancias) {
//		super();
//		this.id = id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}
//	
//	public Peregrino(Long id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial) {
//		super();
//		this.id = id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas.add(paradaInicial);
//	}
	
	public Peregrino() {
		
	}


//	public Peregrino(int id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial,
//			List<Parada> paradas, List<Estancia> estancias) {
//		super();
//		this.id = (long) id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}

//	public Peregrino(int id, String nombre, String nacionalidad, Carnet carnet, List<Parada> paradas,
//			List<Estancia> estancias) {
//		super();
//		this.id = (long) id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = carnet;
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}
	
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

//	public List<Parada> getParadas() {
//		return paradas;
//	}
//
//	public void setParadas(List<Parada> paradas) {
//		this.paradas = paradas;
//	}
//
	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
//
//	public List<Estancia> getEstancias() {
//		return estancias;
//	}
//
//	public void setEstancias(List<Estancia> estancias) {
//		this.estancias = estancias;
//	}
	
	
	//métodos
	
}
