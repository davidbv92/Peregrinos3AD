package com.luisdbb.tarea3AD2024base.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un usuario del sistema con credenciales de autenticación.
 * Esta clase se mapea a la tabla "User" en la base de datos y contiene
 * información de identificación, rol y autenticación del usuario.
 * 
 * @author Ram Alapure
 * @author David Ballesteros
 * @since 05-04-2017
 * @version 1.0
 */
@Entity
@Table(name = "User")
public class User {

	/**
     * Nombre de usuario único (clave primaria)
     */
	@Id
	@Column(unique=true)
	private String usuario;

	/**
     * Rol del usuario (ej: "admin", "user", "moderator")
     */
	private String rol;

	/**
     * Email único del usuario
     */
	@Column(unique=true)
	private String email;

	/**
     * Contraseña del usuario (debería almacenarse encriptada)
     */
	private String password;
	
	

//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	/**
     * Construye un nuevo usuario con los detalles especificados.
     * 
     * @param usuario Nombre de usuario único
     * @param rol Rol/privilegios del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario (debería estar encriptada)
     */
	public User(String usuario, String rol, String email, String password) {
		this.usuario=usuario;
		this.rol=rol;
		this.email=email;
		this.password=password;
	}

	/**
     * Constructor por defecto requerido por JPA
     */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Obtiene el email del usuario
     * 
     * @return la dirección de email
     */
	public String getEmail() {
		return email;
	}

	/**
     * Establece el email del usuario
     * 
     * @param email la nueva dirección de email (debe ser única)
     */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * Obtiene la contraseña del usuario (encriptada)
     * 
     * @return la contraseña encriptada
     */
	public String getPassword() {
		return password;
	}

	/**
     * Establece la contraseña del usuario
     * 
     * @param password la nueva contraseña (debería encriptarse antes de asignarse)
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Obtiene el nombre de usuario
     * 
     * @return el nombre de usuario único
     */
	public String getUsuario() {
		return usuario;
	}

	/**
     * Establece el nombre de usuario
     * 
     * @param usuario el nuevo nombre de usuario único
     */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
     * Obtiene el rol del usuario
     * 
     * @return el rol actual
     */
	public String getRol() {
		return rol;
	}

	/**
     * Establece el rol del usuario
     * 
     * @param rol el nuevo nivel de privilegios/rol
     */
	public void setRol(String rol) {
		this.rol = rol;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", usuario=" + usuario + ", rol=" + rol + ", email=" + email + ", password="
//				+ password + "]";
//	}

	


}
