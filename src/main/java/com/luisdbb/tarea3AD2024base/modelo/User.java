package com.luisdbb.tarea3AD2024base.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Entity
@Table(name = "User")
public class User {

	@Id
	@Column(unique=true)
	private String usuario;

	private String rol;

	@Column(unique=true)
	private String email;

	private String password;
	
	

//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", usuario=" + usuario + ", rol=" + rol + ", email=" + email + ", password="
//				+ password + "]";
//	}

	


}
