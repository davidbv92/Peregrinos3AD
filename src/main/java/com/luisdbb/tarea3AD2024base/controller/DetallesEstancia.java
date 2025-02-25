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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * @author David Ballesteros
 * @since 24-02-2025
 */
@Controller
public class DetallesEstancia implements Initializable{

	@FXML
	private VBox panelServicios;
	
	@FXML
	private ComboBox<String> cbPago;
	@FXML
	private TextField txtExtra;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtLocalidad;
	@FXML
	private TextField txtPeso;
	@FXML
	private TextField txtAlto;
	@FXML
	private TextField txtAncho;
	@FXML
	private TextField txtProfundo;
	@FXML
	private CheckBox checkUrgente;
	
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSellar;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void onSellar() {
		
	}
	
	public void onLimpiar() {
		
	}
	
	public void onVolver() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas volver?, no se guardarán los datos.");
		if(res) {
			stageManager.switchScene(FxmlView.VENTANA_PARADA);
			
		}
	}

}
