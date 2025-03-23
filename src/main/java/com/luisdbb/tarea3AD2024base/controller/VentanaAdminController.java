package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.db4o.DataConnectionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.MongoDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
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
 * Controlador para la ventana de administración.
 * Permite gestionar paradas, servicios, realizar copias de seguridad y acceder a la ayuda.
 * 
 * @author David Ballesteros
 * @since 25-01-2025
 */
@Controller
public class VentanaAdminController implements Initializable{

	@FXML
	private MenuItem itemRegistrarParada;
	@FXML
	private MenuItem itemAyuda;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Button btnRegistrarParada;
	@FXML
	private Button btnCrearServicio;
	@FXML
	private Button btnEditarServicio;
	@FXML
	private Button btnBackup;
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Label lblNumeroPeregrinos;
	@FXML
	private Label lblNumeroParadas;
	@FXML
	private Label lblParadaFrecuente;
	@FXML
	private Label lblPeregrinoActivo;
	@FXML
    private WebView webView;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private MongoDBService mongoDBService;
	
	@Autowired
    private DataConnectionDB4O db4o;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//acelerator
		itemRegistrarParada.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

		//mostrar campos de la izq de la ventana
		lblNumeroPeregrinos.setText("Peregrinos registrados: "+peregrinoService.getTotalPeregrino());
		lblNumeroParadas.setText("Paradas disponibles: "+paradaService.getTotalParada());
		//lblPeregrinoActivo.setText("Peregrino más activo: "+carnetService.getUserWithCarnetMaxDistance().getUsuario());
		//lblParadaFrecuente.setText("");
		
	}
	
	/**
     * Maneja el evento de mostrar la ayuda.
     * Abre una ventana modal con la documentación de ayuda.
     */
	public void onAyuda() {
		//MiAlerta.showInformationAlert("Información accionado");
		mostrarAyuda();
	}
	
	/**
     * Muestra la ventana de ayuda con la documentación HTML.
     */
	private void mostrarAyuda() {
		WebView webView=new WebView();
		String url=getClass().getResource("/help/ventanaAdminHelp.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage=new Stage();
		helpStage.setTitle("Ayuda PEREGRINAPP");
		
		Scene helpScene=new Scene(webView,600,400);
		
		helpStage.setScene(helpScene);
		
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(true);
		helpStage.show();
	}

	/**
     * Maneja el evento de cerrar sesión.
     * Muestra una confirmación antes de cerrar la sesión y volver a la pantalla de inicio de sesión.
     */
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			Sesion.getInstancia().setId(0L);
			Sesion.getInstancia().setNombre("Invitado");
			Sesion.getInstancia().setTipo("Invitado");
			stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	/**
     * Maneja el evento de registrar una nueva parada.
     * Redirige a la pantalla de registro de paradas.
     */
	public void onRegistrarParada() {
		stageManager.switchScene(FxmlView.REGISTRO_PARADA);
	}
	
	/**
     * Maneja el evento de crear un nuevo servicio.
     * Redirige a la pantalla de registro de servicios.
     */
	public void onCrearServicio() {
		stageManager.switchScene(FxmlView.REGISTRO_SERVICIO);
	}
	
	/**
     * Maneja el evento de editar un servicio existente.
     * Redirige a la pantalla de edición de servicios.
     */
	public void onEditarServicio() {
		stageManager.switchScene(FxmlView.EDITAR_SERVICIO);
	}
	
	/**
     * Maneja el evento de realizar una copia de seguridad.
     * Genera un backup de la base de datos MongoDB.
     */
	public void onBackup() {
		mongoDBService.generarBackup();
	}

	

	
}
