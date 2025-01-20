package com.luisdbb.tarea3AD2024base.modelo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MiAlerta {

	public static void showInformationAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showWarningAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showConfirmationAlert(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
