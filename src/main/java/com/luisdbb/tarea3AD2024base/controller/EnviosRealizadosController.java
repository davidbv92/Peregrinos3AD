package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private TableColumn<EnvioACasa, String> colNombre;
	@FXML
    private TableColumn<EnvioACasa, Double> colPrecio;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
