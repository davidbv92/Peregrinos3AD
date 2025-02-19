package com.luisdbb.tarea3AD2024base.modelo;

public class ReportData {

	private Long id;
	private String usuario;
	private String nombre_completo;
	private String nacionalidad;
	private String correo;
	private String parada_inicial;
	private Double distancia;
	private Long nvips;
	private String imagen;
	public ReportData() {
		
	}
	
	public ReportData(Long id, String usuario, String nombre_completo, String nacionalidad, String correo,
			String parada_inicial, Double distancia, Long nvips, String imagen) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.nombre_completo = nombre_completo;
		this.nacionalidad = nacionalidad;
		this.correo = correo;
		this.parada_inicial = parada_inicial;
		this.distancia = distancia;
		this.nvips = nvips;
		this.imagen=imagen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getParada_inicial() {
		return parada_inicial;
	}

	public void setParada_inicial(String parada_inicial) {
		this.parada_inicial = parada_inicial;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Long getNvips() {
		return nvips;
	}

	public void setNvips(Long nvips) {
		this.nvips = nvips;
	}
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
}
