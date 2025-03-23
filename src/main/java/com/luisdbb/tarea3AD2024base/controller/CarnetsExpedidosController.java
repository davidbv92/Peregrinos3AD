package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.xmldb.api.base.XMLDBException;

import com.luisdbb.tarea3AD2024base.existDB.ExistDBManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista que muestra los carnets expedidos en una parada específica.
 * Implementa la interfaz Initializable para inicializar los componentes de la vista.
 * 
 * @author David Ballesteros
 * @since 01-03-2025
 */
@Controller
public class CarnetsExpedidosController implements Initializable{

	/**
     * Tabla que muestra la lista de carnets expedidos.
     */
	@FXML
	private TableView<String> tableView;
	
	/**
     * Columna de la tabla que muestra el nombre de los carnets.
     */
	@FXML
	private TableColumn<String,String> colNombre;
	
	/**
     * Servicio de parada para obtener datos relacionados con la parada.
     */
	@Autowired
	private ParadaService paradaService;

	/**
     * Inicializa los componentes de la vista y carga los carnets expedidos.
     * @param location URL de localización del recurso.
     * @param resources Conjunto de recursos internacionalizados para la vista.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Parada parada=paradaService.find(Sesion.getInstancia().getId());
		colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		try {
			List<String> carnets=ExistDBManager.obtenerArchivosDeColeccion(parada.getNombre().replace(" ", "_")+"_"+parada.getRegion());
			//MiAlerta.showInformationAlert(parada.getNombre().replace(" ", "_")+"_"+parada.getRegion(),carnets.toString());
			ObservableList<String> carnetsObservable = FXCollections.observableArrayList(carnets);
			tableView.setItems(carnetsObservable);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			MiAlerta.showErrorAlert("Error al cargar los carnets", "Se ha producido un error inesperado al cargar los carnets de la parada. Inténtelo de nuevo más tarde.");
		}
	}
	
}
