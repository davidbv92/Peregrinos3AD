package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.objectDB.DataConnectionObjectDB;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.services.ConjuntoContratadoService;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * Controlador para la pantalla de inicio de sesión.
 * Permite a los usuarios iniciar sesión, registrarse o recuperar su contraseña.
 * También gestiona la autenticación de usuarios y la navegación a otras pantallas.
 * 
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
	private TextField txtPasswordVisible;
	
	@FXML
	private Hyperlink linkRegistro;
	@FXML
	private Hyperlink linkRecuperar;
	
	@FXML
	private Button btnInicioSesion;
	@FXML
	private Button btnMostrar;
	@FXML
	private Button btnOcultar;
	
	@FXML
	private FlowPane panelEncriptado;
	@FXML
	private FlowPane panelDesencriptado;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private PeregrinoService peregrinoService;
	
	@Autowired
    private ParadaService paradaService;
	
	@Autowired
    private ServicioService servicioService;
	
//	@Autowired
//    private ConjuntoContratadoService conjuntoContratadoService;
	

	@Value("${admin.username}")
	private String adminUsername;

	@Value("${admin.password}")
	private String adminPassword;
	
	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Sesion.getInstancia().setId(null);
		Sesion.getInstancia().setNombre("Invitado");
		Sesion.getInstancia().setTipo("Invitado");
		
		//ayuda
//        Scene scene = stageManager.getPrimaryStage().getScene();
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.F1) {
//                    mostrarAyuda();
//                }
//            }
//
//			
//        });
		
		//persistir Envío a Casa permanente
		if(servicioService.findByName("Envío a Casa")==null) {
			Servicio servicio=new Servicio(0L,"Envío a Casa",10.0);
			servicioService.save(servicio);
		}
		
	}
	
//	private void mostrarAyuda() {
//		WebView webView=new WebView();
//		String url=getClass().getResource("/help/loginHelp.html").toExternalForm();
//		webView.getEngine().load(url);
//		
//		Stage helpStage=new Stage();
//		helpStage.setTitle("Ayuda PEREGRINAPP");
//		
//		Scene helpScene=new Scene(webView,600,400);
//		
//		helpStage.setScene(helpScene);
//		
//		helpStage.initModality(Modality.APPLICATION_MODAL);
//		helpStage.setResizable(true);
//		helpStage.show();
//		
//	}
	
	/**
     * Maneja el evento de inicio de sesión.
     * Valida las credenciales del usuario y redirige a la pantalla correspondiente.
     */
	public void onIniciarSesion() {
		if (getUsername().equals(adminUsername) && getPassword().equals(adminPassword)) {
	        Sesion.getInstancia().setId(0L);
	        Sesion.getInstancia().setNombre("Administrador");
	        Sesion.getInstancia().setTipo("administrador");
	        stageManager.switchScene(FxmlView.VENTANA_ADMIN);
	        return;
	    }
		//MiAlerta.showInformationAlert("Botón Iniciar Sesión ");
		if(userService.authenticate(getUsername(), getPassword())){
    		User usuario= userService.findByUsuario(getUsername());
    		if(usuario==null) {
    			usuario=userService.findByEmail(getUsername());
    		}
    		switch(usuario.getRol()) {
    			case "administrador":{
    				Sesion.getInstancia().setId(0L);
    		        Sesion.getInstancia().setNombre("Administrador");
    		        Sesion.getInstancia().setTipo("administrador");
    				stageManager.switchScene(FxmlView.VENTANA_ADMIN);
    				break;
    			}case "peregrino":{
    				Peregrino peregrino=peregrinoService.findByUsuario(usuario);
    				Sesion.getInstancia().setId(peregrino.getId());
    				Sesion.getInstancia().setNombre(peregrino.getNombre());
    				Sesion.getInstancia().setTipo("peregrino");
    				stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
    				break;
    			}case "parada":{
    				Parada parada=paradaService.findByUsuario(usuario);
    				Sesion.getInstancia().setId(parada.getId());
    				Sesion.getInstancia().setNombre(parada.getResponsable());
    				Sesion.getInstancia().setTipo("parada");
    				stageManager.switchScene(FxmlView.VENTANA_PARADA);
    				break;
    			}default:{
    				MiAlerta.showErrorAlert("Ha ocurrido un problema con su cuenta, póngase en contacto con el administrador.");
    				break;
    			}
    		}
    		
    	}else{
    		MiAlerta.showWarningAlert("Las credenciales introducidas no encajan con ninguna cuenta existente. Pruebe otra vez o regístrese."); 
    	}
		
	}
	
	/**
     * Obtiene la contraseña ingresada por el usuario.
     *
     * @return La contraseña ingresada.
     */
	public String getPassword() {
		if(panelEncriptado.isVisible()) {
			return txtPassword.getText();
		}else {
			return txtPasswordVisible.getText();
		}
		
	}

	/**
     * Obtiene el nombre de usuario o correo electrónico ingresado por el usuario.
     *
     * @return El nombre de usuario o correo electrónico ingresado.
     */
	public String getUsername() {
		return txtUsuario.getText();
	}

	/**
     * Maneja el evento de recuperar contraseña.
     * Muestra un mensaje indicando que la funcionalidad no está disponible.
     */
	public void onRecuperarPassword() {
		MiAlerta.showInformationAlert("Esta funcionalidad no está disponible en esta versión");
		//MiAlerta.showInformationAlert("Conjuntos", mostrarConjuntos());
	}
	
//	private String mostrarConjuntos() {
//		String sol="";
//		List<ConjuntoContratado> conjuntos=conjuntoContratadoService.findAll();
//		for(ConjuntoContratado c:conjuntos) {
//			sol=sol+c.toString()+"\n";
//		}
//		return sol;
//	}

	/**
     * Maneja el evento de registrarse.
     * Redirige a la pantalla de registro de peregrinos.
     */
	public void onRegistrarse() {
		stageManager.switchScene(FxmlView.REGISTRO_PEREGRINO);
	}
	
	/**
     * Maneja el evento de mostrar la contraseña.
     * Hace visible la contraseña en texto plano.
     */
	public void onMostrar() {
		txtPasswordVisible.setText(txtPassword.getText());
		panelDesencriptado.setVisible(true);
		panelEncriptado.setVisible(false);
		txtPasswordVisible.requestFocus();
	}
	
	/**
     * Maneja el evento de ocultar la contraseña.
     * Oculta la contraseña y la muestra como asteriscos.
     */
	public void onOcultar() {
		txtPassword.setText(txtPasswordVisible.getText());
		panelEncriptado.setVisible(true);
		panelDesencriptado.setVisible(false);
		txtPassword.requestFocus();
	}

}
