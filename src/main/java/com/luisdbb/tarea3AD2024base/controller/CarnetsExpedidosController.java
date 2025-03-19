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

@Controller
public class CarnetsExpedidosController implements Initializable{

	@FXML
	private TableView<String> tableView;
	
	@FXML
	private TableColumn<String,String> colNombre;
	
	@Autowired
	private ParadaService paradaService;

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
