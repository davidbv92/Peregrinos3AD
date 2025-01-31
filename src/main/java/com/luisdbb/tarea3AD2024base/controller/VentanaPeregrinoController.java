package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class VentanaPeregrinoController implements Initializable{

	
	@FXML
	private MenuItem itemMostrarDatos;
	@FXML
	private MenuItem itemModificarDatos;
	@FXML
	private MenuItem itemExportarCarnet;
	@FXML
	private MenuItem itemInformacion;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblCorreo;
	
	@FXML
	private Button btnMostrarDatos;
	@FXML
	private Button btnModificarDatos;
	@FXML
	private Button btnExportarCarnet;
	@FXML
	private Button btnCerrarSesion;

	private Peregrino peregrino;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private VisitaService visitaService;
	@Autowired
	private EstanciaService estanciaService;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//poner shortcuts
		itemMostrarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		itemModificarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
		itemExportarCarnet.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		itemInformacion.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		//objeto peregrino actual
		peregrino=peregrinoService.find(Sesion.getInstancia().getId());
		//personalizar según sesión
		lblTitulo.setText("BIENVENIDO, "+peregrino.getUsuario().getUsuario().toUpperCase());
		lblNombre.setText("Nombre: "+peregrino.getNombre());
		lblUsuario.setText("Usuario: "+peregrino.getUsuario().getUsuario());
		lblCorreo.setText("Correo: "+peregrino.getUsuario().getEmail());
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
	
	public void onExportarCarnet() {
		List<Visita>visitas=visitaService.findByPeregrino_Id(peregrino.getId());
		List<Parada> paradas=new ArrayList<>();
		List<Estancia> estancias=new ArrayList<>();
		for(Visita v:visitas) {
			Parada p=paradaService.find(v.getParada().getId());
			paradas.add(p);
		}
		estancias=estanciaService.findByPeregrino(peregrino);
		peregrino.setParadas(paradas);
		peregrino.setEstancias(estancias);
		Carnet c=carnetService.findByPeregrino_Id(peregrino.getId());
		peregrino.exportarCarnetXML();
		MiAlerta.showInformationAlert("Exportación exitosa","Puede ver su carnet en: src/main/resources/files/"+peregrino.getNombre()+"_peregrino.xml");
	}
	
	public void onMostrarDatos() {
		MiAlerta.showInformationAlert("Mostrar datos accionado");
	}
	
	public void onModificarDatos() {
		MiAlerta.showInformationAlert("Modificar datos accionado");
	}
	
	public void onInformacion() {
		MiAlerta.showInformationAlert("Información accionado");
	}

}
