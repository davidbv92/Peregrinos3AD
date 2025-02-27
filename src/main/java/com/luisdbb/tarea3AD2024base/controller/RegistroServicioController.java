package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.db4o.DataConnectionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
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
	private ServicioService servicioService;
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
			List<Long> ids=new ArrayList<Long>(0);
			for (Parada parada : tableView.getSelectionModel().getSelectedItems()) {
		        ids.add(parada.getId());
		    }
			Servicio s=new Servicio(id,nombre,precio,ids);
			int anterior=servicioService.findAll().size();
			servicioService.save(s);
			int posterior=servicioService.findAll().size();
			MiAlerta.showInformationAlert(anterior+" -> "+posterior);
		}
	}
	
	private Long generarId() {
		Long id=servicioService.calcularIdMaximo()+1;
		return id;
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
		if(precio==null || precio.isBlank() ||precio.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el precio", "El precio no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(precio.length()>9) {
			MiAlerta.showWarningAlert("Error en el precio", "El precio no puede ser tan elevado.");
			return false;
		}else {
			String regex = "^\\d+(\\.\\d{1,2})?$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(precio);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el precio.", "El precio debe ser positivo y seguir el formato X(.XX) de parte entera y opcionalmente parte decimal separada por un (.) y de hasta dos decimales.");
				return false;
	        }
		}
		return true;
	}

	private boolean nombreValido(String nombre) {
		if(nombre==null || nombre.isBlank() ||nombre.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el nombre", "El nombre no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(nombre.length()>50) {
			MiAlerta.showWarningAlert("Error en el nombre", "El nombre no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else if(servicioService.findByName(nombre)!=null){
			MiAlerta.showWarningAlert("Error en el nombre.", "El nombre ya está registrado en la base de datos.");
			return false;
		}else {
			String regex = "^[\\p{L}]+(?: [\\p{L}]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(nombre);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el nombre.", "El nombre debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}

	public void onLimpiar() {
		txtNombre.clear();
		txtPrecio.clear();
		tableView.getSelectionModel().clearSelection();
	}
	
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, se perderán todos los datos introducidos.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.VENTANA_ADMIN);
			
		}
	}
}
