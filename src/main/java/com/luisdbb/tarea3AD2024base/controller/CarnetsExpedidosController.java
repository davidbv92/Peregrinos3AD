package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

@Controller
public class CarnetsExpedidosController implements Initializable{

	@FXML
	private TreeView treeView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TreeItem<String> root = new TreeItem<String>("Root Node");
		 root.setExpanded(true);
		 root.getChildren().addAll(
		     new TreeItem<String>("Item 1"),
		     new TreeItem<String>("Item 2"),
		     new TreeItem<String>("Item 3")
		 );
		
	}
	
}
