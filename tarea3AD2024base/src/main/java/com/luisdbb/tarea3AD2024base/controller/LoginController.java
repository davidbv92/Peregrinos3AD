package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class LoginController implements Initializable{
	
	@FXML
    private TextField txtUsuario;
	@FXML
    private PasswordField txtPassword;
	
	@FXML
	private Hyperlink linkRegistro;
	@FXML
	private Hyperlink linkRecuperar;
	
	@FXML
	private Button btnInicioSesion;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private UserService userService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void onIniciarSesion() {
		//MiAlerta.showInformationAlert("Botón Iniciar Sesión ");
		if(userService.authenticate(getUsername(), getPassword())){
    		User usuario= userService.findByUsuario(getUsername());
    		if(usuario==null) {
    			usuario=userService.findByEmail(getUsername());
    		}
    		switch(usuario.getRol()) {
    			case "administrador":{
    				stageManager.switchScene(FxmlView.VENTANA_ADMIN);
    				break;
    			}case "peregrino":{
    				stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
    				break;
    			}case "parada":{
    				stageManager.switchScene(FxmlView.VENTANA_PARADA);
    				break;
    			}default:{
    				MiAlerta.showInformationAlert("Ha ocurrido un problema con su cuenta, póngase en contacto con el administrador.");
    				break;
    			}
    		}
    		
    	}else{
    		MiAlerta.showInformationAlert("Las credenciales introducidas no encajan con ninguna cuenta existente. Pruebe otra vez o regístrese."); 
    	}
		
	}
	
	public String getPassword() {
		return txtPassword.getText();
	}

	public String getUsername() {
		return txtUsuario.getText();
	}

	public void onRecuperarPassword() {
		MiAlerta.showInformationAlert("Esta funcionalidad no está disponible en esta versión");
	}
	
	public void onRegistrarse() {
		stageManager.switchScene(FxmlView.REGISTRO_PEREGRINO);
	}

}
