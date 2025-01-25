package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class RegistroPeregrinoController implements Initializable{

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtPassword2;
	@FXML
	private TextField txtCorreo;
	@FXML
	private ComboBox cbNacionalidad;
	@FXML
	private ComboBox cbParadaInicial;
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
	private PeregrinoService peregrinoService;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private VisitaService visitaService;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//cargar los combobox
		cargarNacionalidades();
		
		cbParadaInicial.getItems().add("1");
		cbParadaInicial.getItems().add("2");
		
	}

	private void cargarNacionalidades() {
		try {
            File xmlFile = new File("src/main/resources/files/paises.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nombres = document.getElementsByTagName("nombre");

            for (int i = 0; i < nombres.getLength(); i++) {
                String nombrePais = nombres.item(i).getTextContent();
                cbNacionalidad.getItems().add(nombrePais);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public void onLimpiar() {
		txtNombre.clear();
		txtUsuario.clear();
		txtPassword.clear();
		txtPassword2.clear();
		txtCorreo.clear();
		//cbNacionalidad
		//cbParadaInicial
	}
	
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, se perderán todos los datos introducidos.");
		if(res) {
			onLimpiar();
			stageManager.switchScene(FxmlView.LOGIN);
			
		}
	}
	
	public void onRegistrar() {
		if(camposValidos()) {
			registrarPeregrino();
		}
	}

	private void registrarPeregrino() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String nombre=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		String correo=txtCorreo.getText();
		Long idParada=Long.parseLong( (String) cbParadaInicial.getValue());
		
		
		Parada parada=paradaService.find(idParada);	
				
		Peregrino peregrino=new Peregrino();
		Carnet carnet=new Carnet();
		User usuario=new User();
		Visita visita=new Visita();
		
		usuario.setUsuario(username);
		usuario.setEmail(correo);
		usuario.setPassword(password);
		usuario.setRol("peregrino");
		
		peregrino.setNombre(nombre);
		peregrino.setNacionalidad(nacionalidad);
		peregrino.setUsuario(usuario);
		
		carnet.setDistancia(0);
		carnet.setFechaexp(LocalDate.now());
		carnet.setNvips(0);
		carnet.setPeregrino(peregrino);
		carnet.setParadaInicial(parada);
		
		peregrino.setCarnet(carnet);
		
		visita.setParada(parada);
		visita.setPeregrino(peregrino);
		visita.setFecha(LocalDate.now());
		
		Peregrino newPeregrino=peregrinoService.save(peregrino);
		Visita newVisita=visitaService.save(visita);
		
		onLimpiar();
		saveAlert(newPeregrino);
		Sesion.getInstancia().setId(newPeregrino.getId());
		Sesion.getInstancia().setNombre(newPeregrino.getNombre());
		Sesion.getInstancia().setTipo("peregrino");
		stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
	}

	private void saveAlert(Peregrino p) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Peregrino insertado: id: "+p.getId()+ " nombre: "+ p.getNombre()+" carnet(id): "+p.getCarnet().getId());
		alert.showAndWait();
	}

	private boolean camposValidos() {
		// TODO Auto-generated method stub
		return true;
	}
}
