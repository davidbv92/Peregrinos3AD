package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.db4o.DataConnectionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author David Ballesteros
 * @since 20-02-2025
 */
@Controller
public class RegistroServicioController implements Initializable{

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPrecio;
	
	@FXML
    private TableView<Parada> tableView;
    @FXML
    private TableColumn<Parada, Long> colId;
    @FXML
    private TableColumn<Parada, String> colNombre;
    @FXML
    private TableColumn<Parada, String> colRegion;
    
    @FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnCrear;
	
    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
	private ParadaService paradaService;
    @Autowired
    private DataConnectionDB4O db4o;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//preparar tabla
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		
        //cargar Tabla
        cargarDatosTabla();
        
        //listener para no permitir precios no correctos
	}

	private void cargarDatosTabla() {
	    List<Parada> paradas = paradaService.findAll();
	    ObservableList<Parada> observableList = FXCollections.observableArrayList(paradas);
	    tableView.setItems(observableList);
		
	}

	public void onCrear() {
		if(datosValidos()) {
			Long id=generarId();
			String nombre=txtNombre.getText();
			double precio=Double.parseDouble(txtPrecio.getText());
			List<Long> ids=new ArrayList<Long>();
		}
	}
	
	private Long generarId() {
		// TODO Auto-generated method stub
		return 1000L;
	}

	private boolean datosValidos() {
		String nombre=txtNombre.getText();
		String precio=txtPrecio.getText();
		if(!nombreValido(nombre)) {
			return false;
		}if(!precioValido(precio)) {
			return false;
		}
		return true;
	}

	private boolean precioValido(String precio) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean nombreValido(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLimpiar() {
		txtNombre.clear();
		txtPrecio.clear();
	}
	
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, se perderán todos los datos introducidos.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.VENTANA_ADMIN);
			
		}
	}
}
