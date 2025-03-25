package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Clase utilitaria para mostrar alertas/ventanas emergentes en aplicaciones JavaFX.
 * Proporciona métodos estáticos para diferentes tipos de alertas: información, advertencia,
 * error y confirmación.
 * 
 * @author David Ballesteros
 * @since 23-01-2025
 * @version 1.0
 */
public class MiAlerta {

	/**
     * Muestra una alerta de información con un mensaje personalizado.
     * 
     * @param message El mensaje a mostrar en el contenido de la alerta.
     */
	public static void showInformationAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
     * Muestra una alerta de información con título y mensaje personalizados.
     * 
     * @param titulo El texto que aparecerá en el encabezado de la alerta.
     * @param message El mensaje a mostrar en el contenido de la alerta.
     */
	public static void showInformationAlert(String titulo,String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(titulo);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
     * Muestra una alerta de advertencia con un mensaje personalizado.
     * 
     * @param message El mensaje de advertencia a mostrar.
     */
	public static void showWarningAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
     * Muestra una alerta de advertencia con título y mensaje personalizados.
     * 
     * @param titulo El texto que aparecerá en el encabezado de la alerta.
     * @param message El mensaje de advertencia a mostrar.
     */
	public static void showWarningAlert(String titulo,String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText(titulo);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
     * Muestra una alerta de error con un mensaje personalizado.
     * 
     * @param message El mensaje de error a mostrar.
     */
	public static void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
     * Muestra una alerta de error con título y mensaje personalizados.
     * 
     * @param titulo El texto que aparecerá en el encabezado de la alerta.
     * @param message El mensaje de error a mostrar.
     */
	public static void showErrorAlert(String titulo,String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(titulo);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
     * Muestra una alerta de confirmación con un mensaje personalizado.
     * 
     * @param message El mensaje de confirmación a mostrar.
     * @return true si el usuario presionó OK, false si canceló o cerró la ventana.
     */
	public static boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
	
	/**
     * Muestra una alerta de confirmación con título y mensaje personalizados.
     * 
     * @param titulo El texto que aparecerá en el encabezado de la alerta.
     * @param message El mensaje de confirmación a mostrar.
     * @return true si el usuario presionó OK, false si canceló o cerró la ventana.
     */
	public static boolean showConfirmationAlert(String titulo,String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText(titulo);
		alert.setContentText(message);
		
		Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
	}
}
