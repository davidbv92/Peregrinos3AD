package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * @author David Ballesteros
 * @since 25-01-2025
 */
@Controller
public class RegistroParadaController implements Initializable{

	@FXML
	private TextField txtResponsable;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtPassword2;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtNombreParada;
	@FXML
	private TextField txtRegion;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnRegistrar;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	@Autowired
	private ParadaService paradaService;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void onLimpiar() {
		txtResponsable.clear();
		txtUsuario.clear();
		txtPassword.clear();
		txtPassword2.clear();
		txtCorreo.clear();
		txtNombreParada.clear();
		txtRegion.clear();
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
			registrarParada();
		}
	}
	
	private void registrarParada() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String nombreParada=txtNombreParada.getText();
		String region=txtRegion.getText();
		String correo=txtCorreo.getText();
		String responsable=txtResponsable.getText();
		char regionC=region.charAt(0);
		
		Parada parada=new Parada();
		User usuario=new User();
		
		usuario.setUsuario(username);
		usuario.setEmail(correo);
		usuario.setPassword(password);
		usuario.setRol("parada");
		
		parada.setNombre(nombreParada);
		parada.setRegion(regionC);
		parada.setUsuario(usuario);
		parada.setResponsable(responsable);
		
		Parada newParada=paradaService.save(parada);
		
		onLimpiar();
		saveAlert(newParada);
		stageManager.switchScene(FxmlView.VENTANA_ADMIN);
	}

	private void saveAlert(Parada p) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Parada insertada: id: "+p.getId()+ " nombre: "+ p.getNombre());
		alert.showAndWait();
	}

	private boolean camposValidos() {
		// TODO Auto-generated method stub
		return true;
	}
}
