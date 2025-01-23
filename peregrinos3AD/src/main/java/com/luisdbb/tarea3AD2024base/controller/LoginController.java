package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */

public class LoginController implements Initializable{
	
	@FXML
    private TextField txtUsuario;
	@FXML
    private PasswordField txtPassword;
	
	@FXML
	private Hyperlink linkRegistro;
	@FXML
	private Hyperlink linkRecuperar;
	
	@FXML
	private Button btnInicioSesion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void onIniciarSesion() {
		MiAlerta.showInformationAlert("Botón Iniciar Sesión ");
	}

}
