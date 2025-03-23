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
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador para la edición de servicios.
 * Permite modificar el nombre, precio y paradas asociadas a un servicio.
 * 
 * @author David Ballesteros
 * @since 04-02-2025
 */
@Controller
public class EditarServiciosController implements Initializable{

	@FXML
	private ComboBox<String> cbId;
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
	private Button btnEditar;
	
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
	private ParadaService paradaService;
    @Autowired
	private ServicioService servicioService;
    @Autowired
    private DataConnectionDB4O db4o;
    
    private String nombreAntiguo="";
    private Servicio servicio=null;
    
    /**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//resetear servicio
		servicio=null;
		//preparar tabla
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		        
		//cargar datos
		cargarCbIds();
		cargarDatosTabla();
		//actualizar con el combobox
		cbId.valueProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null && !newValue.isEmpty()) {
	            long servicioId = obtenerIdServicio(newValue);
	            servicio = servicioService.findById(servicioId);
	            if (servicio != null) {
	                txtNombre.setText(servicio.getNombre());
	                nombreAntiguo=servicio.getNombre();
	                if(servicio.getNombre().equals("Envío a Casa")) {
	                	txtNombre.setDisable(true);
	                }else {
	                	txtNombre.setDisable(false);
	                }
	                txtPrecio.setText(String.valueOf(servicio.getPrecio()));
	                marcarParadasSeleccionadas(servicio);
	            }
	        }
	    });
	}

	/**
     * Carga los datos de las paradas en la tabla.
     */
	private void cargarDatosTabla() {
	    List<Parada> paradas = paradaService.findAll();
	    ObservableList<Parada> observableList = FXCollections.observableArrayList(paradas);
	    tableView.setItems(observableList);
		
	}

	/**
     * Marca las paradas seleccionadas en la tabla.
     *
     * @param servicio El servicio cuyas paradas se deben marcar.
     */
	private void marcarParadasSeleccionadas(Servicio servicio) {
		tableView.getSelectionModel().clearSelection();
	    List<Long> idsParadas = servicio.getParadas();
	    for (Parada parada : tableView.getItems()) {
	        if (idsParadas.contains(parada.getId())) {
	            tableView.getSelectionModel().select(parada);
	        }
	    }
	}

	/**
     * Obtiene el ID del servicio a partir de la cadena seleccionada en el ComboBox.
     *
     * @param newValue La cadena seleccionada en el ComboBox.
     * @return El ID del servicio.
     */
	private long obtenerIdServicio(String newValue) {
		String idString = newValue.split(" ")[0];
	    return Long.parseLong(idString);
	}

	/**
     * Carga los IDs de los servicios en el ComboBox.
     */
	private void cargarCbIds() {
		List<Servicio> servicios=servicioService.findAll();
		for(Servicio s:servicios) {
			String cadena=mapearServicio(s);
			cbId.getItems().add(cadena);
		}
		
	}

	/**
     * Mapea un servicio a una cadena para mostrarlo en el ComboBox.
     *
     * @param s El servicio a mapear.
     * @return Una cadena que representa el servicio.
     */
	private String mapearServicio(Servicio s) {
		return s.getId()+" ("+s.getNombre()+")";
	}
	
	/**
     * Maneja el evento de limpiar los campos del formulario.
     */
	public void onLimpiar() {
		if(!txtNombre.isDisabled()) {
			txtNombre.setText("");
		}
		if(!txtPrecio.isDisabled()) {
			txtPrecio.setText("");
		}
		tableView.getSelectionModel().clearSelection();
	}
	
	/**
     * Maneja el evento de editar un servicio.
     */
	public void onEditar() {
		if(datosValidos()) {
			boolean res=MiAlerta.showConfirmationAlert("¿Desea editar el servicio?", "El servicio será modificado con los nuevos valores definitivamente en el sistema.");
			if(res) {
				String nombre=txtNombre.getText();
				double precio=Double.parseDouble(txtPrecio.getText());
				List<Long> ids=new ArrayList<Long>(0);
				for (Parada parada : tableView.getSelectionModel().getSelectedItems()) {
			        ids.add(parada.getId());
			    }
				
				servicio.setNombre(nombre);
				servicio.setPrecio(precio);
				servicio.setParadas(ids);
				
				//int anterior=servicioService.findAll().size();
				servicioService.update(servicio);
				//int posterior=servicioService.findAll().size();
				MiAlerta.showInformationAlert("Edición exitosa", "Los nuevos valores se han registrado correctamente");
				stageManager.switchScene(FxmlView.VENTANA_ADMIN);
			}
			
		}
	}

	/**
     * Valida los datos ingresados por el usuario.
     *
     * @return true si los datos son válidos, false en caso contrario.
     */
	private boolean datosValidos() {
		String nombre=txtNombre.getText();
		String precio=txtPrecio.getText();
		if(!servicioValido()) {
			return false;
		}else if(!nombreValido(nombre)) {
			return false;
		}if(!precioValido(precio)) {
			return false;
		}
		return true;
	}
	
	/**
     * Valida que se haya seleccionado un servicio.
     *
     * @return true si el servicio es válido, false en caso contrario.
     */
	private boolean servicioValido() {
		if(servicio==null) {
			MiAlerta.showWarningAlert("Error en la selección del servicio", "Debes escoger un servicio para editar.");
			return false;
		}else {
			if(servicio.getNombre()==null || servicio.getNombre().isBlank() ||servicio.getNombre().isEmpty()) {
				MiAlerta.showWarningAlert("Error en la selección del servicio", "Debes escoger un servicio para editar.");
				return false;
			}
			else {
				//MiAlerta.showInformationAlert(servicio.toString(),"Servicio válido");
				return true;
			}
		}
	}

	/**
     * Valida el precio ingresado.
     *
     * @param precio El precio a validar.
     * @return true si el precio es válido, false en caso contrario.
     */
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

	/**
     * Valida el nombre ingresado.
     *
     * @param nombre El nombre a validar.
     * @return true si el nombre es válido, false en caso contrario.
     */
	private boolean nombreValido(String nombre) {
		if(nombre==null || nombre.isBlank() ||nombre.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el nombre", "El nombre no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(nombre.equals(nombreAntiguo)) {
			return true;
		}else if(nombre.length()>50) {
			MiAlerta.showWarningAlert("Error en el nombre", "El nombre no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else if(servicioService.findByName(nombre)!=null){
			MiAlerta.showWarningAlert("Error en el nombre.", "El nombre ya está registrado en la base de datos.");
			return false;
		}else {
			String regex = "^[A-Za-z]+(?: [A-Za-z]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(nombre);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el nombre.", "El nombre debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}


	/**
     * Maneja el evento de salir de la ventana de edición.
     */
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, no se realizará ninguna modificación.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.VENTANA_ADMIN);
			
		}
	}
	
	
}
