package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.existDB.ExistDBManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author David Ballesteros
 * @since 25-01-2025
 */
@Controller
public class RegistroParadaController implements Initializable{

	@FXML
	private TextField txtResponsable;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtPassword2;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtNombreParada;
	@FXML
	private TextField txtRegion;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSalir;
	@FXML
	private Button btnRegistrar;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private UserService userService;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ICONOS
		btnLimpiar.setGraphic(crearIcono("/images/003-escoba.png"));        
		btnSalir.setGraphic(crearIcono("/images/002-error.png"));
		btnRegistrar.setGraphic(crearIcono("/images/004-marca-de-verificacin.png"));
		
	}
	
	private Node crearIcono(String string) {
		Image imagen=new Image(getClass().getResourceAsStream(string));
        ImageView viewImagen=new ImageView(imagen);
        viewImagen.setFitHeight(16);
        viewImagen.setFitWidth(16);
        return viewImagen;

	}

	public void onLimpiar() {
		txtResponsable.clear();
		txtUsuario.clear();
		txtPassword.clear();
		txtPassword2.clear();
		txtCorreo.clear();
		txtNombreParada.clear();
		txtRegion.clear();
	}
	
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, se perderán todos los datos introducidos.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.VENTANA_ADMIN);
			
		}
	}
	
	public void onRegistrar() {
		
		if(camposValidos()) {
			boolean res=MiAlerta.showConfirmationAlert("¿Quieres insertar los siguientes datos?", mostrarDatosRegistro());
			if(res) {
				registrarParada();
			}
			
		}
	}
	
	private String mostrarDatosRegistro() {
		String username=txtUsuario.getText();
		String nombreParada=txtNombreParada.getText();
		String region=txtRegion.getText();
		String correo=txtCorreo.getText();
		String responsable=txtResponsable.getText();
		char regionC=region.charAt(0);
		
		String res="Nombre: "+ nombreParada+"\n"+
				"Region: "+ regionC+"\n"+
				"Usuario: "+ username+"\n"+
				"Correo: "+ correo+"\n"+
				"Responsable: "+ responsable+"\n"
				;
		return res;
	}

	private void registrarParada() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String nombreParada=txtNombreParada.getText();
		String region=txtRegion.getText();
		String correo=txtCorreo.getText();
		String responsable=txtResponsable.getText();
		char regionC=region.charAt(0);
		
		Parada parada=new Parada();
		User usuario=new User();
		
		usuario.setUsuario(username);
		usuario.setEmail(correo);
		usuario.setPassword(password);
		usuario.setRol("parada");
		
		parada.setNombre(nombreParada);
		parada.setRegion(regionC);
		parada.setUsuario(usuario);
		parada.setResponsable(responsable);
		
		Parada newParada=paradaService.save(parada);
		ExistDBManager.createCollection(newParada.getNombre().replace(" ", "_")+"_"+newParada.getRegion());
		onLimpiar();
		//saveAlert(newParada);
		MiAlerta.showInformationAlert("Parada registrada con éxito", "Se ha registrado la parada "+newParada.getNombre()+" ("+newParada.getRegion()+") en la aplicación.");
		stageManager.switchScene(FxmlView.VENTANA_ADMIN);
	}

	private boolean camposValidos() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String password2=txtPassword2.getText();
		String nombreParada=txtNombreParada.getText();
		String region=txtRegion.getText();
		String correo=txtCorreo.getText();
		String responsable=txtResponsable.getText();
		
		if(!usuarioValido(username)) {
			return false;
		}else if(!passwordValida(password,password2)) {
			return false;
		}else if(!datosParadaValidos(nombreParada,region)) {
			return false;
		}else if(!correoValido(correo)) {
			return false;
		}else if(!responsableValido(responsable)) {
			return false;
		}
		
		return true;
	}

	private boolean responsableValido(String responsable) {
		if(responsable==null || responsable.isBlank() ||responsable.isEmpty()) {
			MiAlerta.showErrorAlert("Error en el nombre del responsable.", "El nombre del responsable no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(responsable.length()>50) {
			MiAlerta.showErrorAlert("Error en el nombre del responsable.", "El nombre del responsable no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
	        String regex = "^[A-Za-z]+(?: [A-Za-z]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(responsable);
	        if(!matcher.matches()) {
	        	MiAlerta.showErrorAlert("Error en el nombre del responsable.", "El nombre del responsable debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}

	private boolean correoValido(String correo) {
		if(correo==null || correo.isBlank() ||correo.isEmpty()) {
			MiAlerta.showErrorAlert("Error en el correo electrónico", "El correo electrónico no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(correo.length()>50) {
			MiAlerta.showErrorAlert("Error en el correo electrónico", "El nombre de usuario no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
			String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(correo);
	        if(!matcher.matches()) {
	        	MiAlerta.showErrorAlert("Error en el correo electrónico", "El formato del correo electrónico no es válido.");
				return false;
	        }
		}
		List<User> usuarios=userService.findAll();
		for(User u:usuarios) {
			if(u.getEmail().toLowerCase().equals(correo.toLowerCase())) {
				MiAlerta.showWarningAlert("Error en el correo electrónico", "El correo ya está registrado en la aplicación.");
				return false;
			}
		}
		return true;
	}

	private boolean datosParadaValidos(String nombreParada, String region) {
		if(nombreParada==null || nombreParada.isBlank() ||nombreParada.isEmpty()) {
			MiAlerta.showErrorAlert("Error en el nombre de la parada.", "El nombre de la parada no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(nombreParada.length()>50) {
			MiAlerta.showErrorAlert("Error en el nombre de la parada.", "El nombre de la parada no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
	        String regex = "^[A-Za-z]+(?: [A-Za-z]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(nombreParada);
	        if(!matcher.matches()) {
	        	MiAlerta.showErrorAlert("Error en el nombre de la parada.", "El nombre de la parada debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		
		if(region==null || region.isBlank() ||region.isEmpty()) {
			MiAlerta.showErrorAlert("Error en la región de la parada.", "La regiónde la parada no puede estar vacía ni ser solo espacios en blanco.");
			return false;
		}else if(region.length()>1) {
			MiAlerta.showErrorAlert("Error en la región de la parada.", "La región de la parada no puede tener una longitud superior a 1 caracter.");
			return false;
		}else {
	        char c=region.charAt(0);
	        if(!Character.isAlphabetic(c)) {
	        	MiAlerta.showErrorAlert("Error en la región de la parada.", "La región de la parada debe ser una letra.");
				return false;
	        }
		}
		
		
		List<Parada> paradas=paradaService.findAll();
		for(Parada p:paradas) {
			char regionC=region.charAt(0);
			if(p.getNombre().equals(nombreParada)) {
				if(p.getRegion()==regionC) {
					MiAlerta.showWarningAlert("Error en el nombre de la parada.", "Esa parada con ese nombre y esa región ya ha sido registrada.");
					return false;
				}
				
			}
		}
		
		return true;
	}

	private boolean passwordValida(String password, String password2) {
		if(password==null || password.isBlank() ||password.isEmpty()) {
			MiAlerta.showErrorAlert("Error en la contraseña", "La contraseña no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(password.length()>50) {
			MiAlerta.showErrorAlert("Error en la contraseña", "La contraseña no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
			for(int i=0;i<password.length();i++) {
				char c=password.charAt(i);
				if(Character.isSpaceChar(c)) {
					MiAlerta.showErrorAlert("Error en la contraseña", "Introdujiste espacios (fallo en la posición "+i+" de la contraseña).");
					return false;
				}
			}
		}
		if(!password.equals(password2)) {
			MiAlerta.showErrorAlert("Error en la contraseña", "Las dos contraseñas introducidas no coinciden.");
			return false;
		}
		return true;
	}

	private boolean usuarioValido(String username) {
		if(username==null || username.isBlank() ||username.isEmpty()) {
			MiAlerta.showErrorAlert("Error en el nombre de usuario", "El nombre de usuario no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(username.length()>50) {
			MiAlerta.showErrorAlert("Error en el nombre de usuario", "El nombre de usuario no puede tener una longitud superior a 50 caracteres.");
			return false;
		
		}else if(username.toLowerCase().equals("admin")){
			MiAlerta.showWarningAlert("Error en el nombre de usuario.", "El nombre de usuario ya está registrado en la base de datos.");
			return false;
		}else {
			for(int i=0;i<username.length();i++) {
				char c=username.charAt(i);
				if(!(Character.isAlphabetic(c)|| Character.isDigit(c)) ) {
					MiAlerta.showWarningAlert("Error en el nombre de usuario.", "No introdujiste solo letras o números(fallo en la posición "+i+" del nombre)");
					return false;
				}
			}
		}
		List<User> usuarios=userService.findAll();
		for(User u:usuarios) {
			if(u.getUsuario().toLowerCase().equals(username.toLowerCase())) {
				MiAlerta.showWarningAlert("Error en el nombre de usuario.", "El nombre de usuario ya está registrado en la base de datos.");
				return false;
			}
		}
		return true;
	}
}
