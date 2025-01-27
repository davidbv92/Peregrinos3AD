package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * @author David Ballesteros
 * @since 25-01-2025
 */
@Controller
public class VentanaAdminController implements Initializable{

	@FXML
	private MenuItem itemRegistrarParada;
	@FXML
	private MenuItem itemInformacion;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Button btnRegistrarParada;
	@FXML
	private Button btnCerrarSesion;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void onInformacion() {
		MiAlerta.showInformationAlert("Información accionado");
	}
	
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	public void onRegistrarParada() {
		stageManager.switchScene(FxmlView.REGISTRO_PARADA);
	}
	
	
	

	
}
