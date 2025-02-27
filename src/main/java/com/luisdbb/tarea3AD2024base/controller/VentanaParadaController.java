package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private MenuItem itemAyuda;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblRegion;
	@FXML
	private Label lblResponsable;
	
	
	@FXML
	private Button btnMostrarDatos;
	@FXML
	private Button btnSellarCarnet;
	@FXML
	private Button btnExportarDatos;
	@FXML
	private Button btnVerEnvios;
	@FXML
	private Button btnCerrarSesion;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private ParadaService paradaService;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//poner shortcuts
		itemMostrarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		itemExportarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		itemSellarCarnet.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		Parada parada=paradaService.find(Sesion.getInstancia().getId());
		//personalizar según sesión
		lblTitulo.setText("BIENVENIDO, "+Sesion.getInstancia().getNombre().toUpperCase());
		lblNombre.setText("Nombre: "+parada.getNombre());
		lblRegion.setText("Región: "+parada.getRegion());
		lblResponsable.setText("Responsable: "+parada.getResponsable());
		
	}

	
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			Sesion.getInstancia().setId(0L);
			Sesion.getInstancia().setNombre("Invitado");
			Sesion.getInstancia().setTipo("Invitado");
			stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	public void onSellarCarnet() {
		//MiAlerta.showInformationAlert("Sellar Carnet accionado");
		stageManager.switchScene(FxmlView.VENTANA_SELLADO);
	}
	
	public void onMostrarDatos() {
		//MiAlerta.showInformationAlert("Mostrar datos accionado");
		stageManager.openModal(FxmlView.DATOS_PARADA);
	}
	
	public void onExportarDatos() {
//		MiAlerta.showInformationAlert("Exportar datos accionado");
		stageManager.openModal(FxmlView.DETALLES_ESTANCIA);
	}
	
	public void onAyuda() {
		//MiAlerta.showInformationAlert("Información accionado");
		mostrarAyuda();
	}
	
	private void mostrarAyuda() {
		WebView webView=new WebView();
		String url=getClass().getResource("/help/ventanaParadaHelp.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage=new Stage();
		helpStage.setTitle("Ayuda PEREGRINAPP");
		
		Scene helpScene=new Scene(webView,600,400);
		
		helpStage.setScene(helpScene);
		
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(true);
		helpStage.show();
	}
	
	public void onVerEnvios() {
		stageManager.openModal(FxmlView.ENVIOS_REALIZADOS);
	}
	
}
