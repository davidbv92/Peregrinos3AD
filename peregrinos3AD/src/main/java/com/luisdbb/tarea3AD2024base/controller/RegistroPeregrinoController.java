package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class RegistroPeregrinoController implements Initializable{

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtPassword2;
	@FXML
	private TextField txtCorreo;
	@FXML
	private ComboBox cbNacionalidad;
	@FXML
	private ComboBox cbParadaInicial;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnRegistrar;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//cargar los combobox
		cbNacionalidad.getItems().add("Español");
		cbNacionalidad.getItems().add("Alemán");
		
		cbParadaInicial.getItems().add(1);
		cbParadaInicial.getItems().add(2);
		
	}

	public void onLimpiar() {
		txtNombre.clear();
		txtUsuario.clear();
		txtPassword.clear();
		txtPassword2.clear();
		txtCorreo.clear();
		//cbNacionalidad
		//cbParadaInicial
	}
	
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, se perderán todos los datos introducidos.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.LOGIN);
			
		}
	}
	
	public void onRegistrar() {
		if(camposValidos()) {
			Carnet c=new Carnet()
			Peregrino p=new Peregrino();
		}
	}

	private boolean camposValidos() {
		// TODO Auto-generated method stub
		return true;
	}
}
