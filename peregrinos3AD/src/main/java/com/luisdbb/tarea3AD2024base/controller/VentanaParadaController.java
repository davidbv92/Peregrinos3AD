package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class VentanaParadaController implements Initializable {

	@FXML
	private MenuItem itemMostrarDatos;
	@FXML
	private MenuItem itemExportarDatos;
	@FXML
	private MenuItem itemSellarCarnet;
	@FXML
	private MenuItem itemInformacion;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Label lblTitulo;
	
	@FXML
	private Button btnMostrarDatos;
	@FXML
	private Button btnSellarCarnet;
	@FXML
	private Button btnExportarDatos;
	@FXML
	private Button btnCerrarSesion;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//personalizar según sesión
		lblTitulo.setText("BIENVENIDO, "+Sesion.getInstancia().getNombre());
		
	}

	
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	public void onSellarCarnet() {
		MiAlerta.showInformationAlert("Sellar Carnet accionado");
	}
	
	public void onMostrarDatos() {
		MiAlerta.showInformationAlert("Mostrar datos accionado");
	}
	
	public void onExportarDatos() {
		MiAlerta.showInformationAlert("Exportar datos accionado");
	}
	
	public void onInformacion() {
		MiAlerta.showInformationAlert("Información accionado");
	}
	
}
