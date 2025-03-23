package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.objectDB.DataConnectionObjectDB;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;

import jakarta.persistence.TypedQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador para la visualización de los envíos realizados.
 * Muestra una tabla con los envíos realizados por la parada actual.
 * 
 * @author David Ballesteros
 * @since 24-02-2025
 */
@Controller
public class EnviosRealizadosController implements Initializable{

	@FXML
	private Label lblNombre;
	@FXML
	private Label lblRegion;
	@FXML
	private Label lblResponsable;
	@FXML
	private Label lblEnvios;
	@FXML
	private TableView<EnvioACasa> tableView;
	@FXML
    private TableColumn<EnvioACasa, Long> colId;
	@FXML
    private TableColumn<EnvioACasa, String> colDireccion;
	@FXML
    private TableColumn<EnvioACasa, String> colLocalidad;
	@FXML
    private TableColumn<EnvioACasa, String> colDimensiones;
	@FXML
    private TableColumn<EnvioACasa, Double> colPeso;
	@FXML
    private TableColumn<EnvioACasa, Boolean> colUrgente;
	
	
	@Autowired
    private EnvioACasaService envioACasaService;
	
	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colDireccion.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion())
    );
        colLocalidad.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getDireccion().getLocalidad())
    );
        colDimensiones.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().mostrarDimensiones())
    );
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colUrgente.setCellValueFactory(new PropertyValueFactory<>("urgente"));
        
        
        cargarDatosTabla();
		
	}

	/**
     * Carga los datos de los envíos realizados en la tabla.
     */
	private void cargarDatosTabla() {
		List<EnvioACasa> listaEnvios = envioACasaService.findByIdParada(Sesion.getInstancia().getId());
	    ObservableList<EnvioACasa> observableList = FXCollections.observableArrayList(listaEnvios);
	    tableView.setItems(observableList);
		
	}

}
