package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
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
	

	@Value("${admin.username}")
	private String adminUsername;

	@Value("${admin.password}")
	private String adminPassword;
	
	
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
	
	public String getPassword() {
		if(panelEncriptado.isVisible()) {
			return txtPassword.getText();
		}else {
			return txtPasswordVisible.getText();
		}
		
	}

	public String getUsername() {
		return txtUsuario.getText();
	}

	public void onRecuperarPassword() {
		MiAlerta.showInformationAlert("Esta funcionalidad no está disponible en esta versión");
//		int[] dim= {3,3,3};
//		EnvioACasa e=new EnvioACasa(5L,5L,2.0,dim,false,new Direccion(1L,"d2","l2"), 1L) ;
//		EnvioACasa e2=new EnvioACasa(3L,3L,2.0,dim,false,new Direccion(1L,"d3","l3"), 1L) ;
//		DataConnectionObjectDB db=new DataConnectionObjectDB();
//		db.init();
//		db.getEntityManager().getTransaction().begin();
//		
//		db.getEntityManager().persist(e);
//		db.getEntityManager().getTransaction().commit();
//		db.closeConnection();
//		
//		db.init();
//		db.getEntityManager().getTransaction().begin();
//		db.getEntityManager().persist(e2);
//		db.getEntityManager().getTransaction().commit();
//		db.closeConnection();
		
//		envioACasaService.save(e);
	}
	
	public void onRegistrarse() {
		stageManager.switchScene(FxmlView.REGISTRO_PEREGRINO);
	}
	
	public void onMostrar() {
		txtPasswordVisible.setText(txtPassword.getText());
		panelDesencriptado.setVisible(true);
		panelEncriptado.setVisible(false);
		txtPasswordVisible.requestFocus();
	}
	
	public void onOcultar() {
		txtPassword.setText(txtPasswordVisible.getText());
		panelEncriptado.setVisible(true);
		panelDesencriptado.setVisible(false);
		txtPassword.requestFocus();
	}

}
