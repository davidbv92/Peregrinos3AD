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
    private TableColumn<EnvioACasa, Double> colPrecio;
	@FXML
    private TableColumn<EnvioACasa, String> colDimensiones;
	@FXML
    private TableColumn<EnvioACasa, Double> colPeso;
	@FXML
    private TableColumn<EnvioACasa, Boolean> colUrgente;
	
	
	@Autowired
    private EnvioACasaService envioACasaService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colDireccion.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion()+" ("+cellData.getValue().getDireccion().getLocalidad()+")")
    );
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDimensiones.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().mostrarDimensiones())
    );
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colUrgente.setCellValueFactory(new PropertyValueFactory<>("urgente"));
        
        
        cargarDatosTabla();
		
	}


	private void cargarDatosTabla() {
		List<EnvioACasa> listaEnvios = envioACasaService.findByIdParada(Sesion.getInstancia().getId());
	    ObservableList<EnvioACasa> observableList = FXCollections.observableArrayList(listaEnvios);
	    tableView.setItems(observableList);
		
	}

}
