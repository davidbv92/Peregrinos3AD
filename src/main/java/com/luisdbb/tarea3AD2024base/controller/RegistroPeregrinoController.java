package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.XMLDBException;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.existDB.ExistDBManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	private ComboBox<String> cbNacionalidad;
	@FXML
	private ComboBox<String> cbParadaInicial;
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
	private VisitaService visitaService;
	@Autowired
	private UserService userService;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//cargar los combobox
		cargarNacionalidades();
		cargarParadas();
		
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

	private void cargarParadas() {
		List<Parada> paradas=paradaService.findAll();
		List<String> datos=convertirParadasString(paradas);
		for(String d:datos) {
			cbParadaInicial.getItems().add(d);
		}
		
		
	}

	private List<String> convertirParadasString(List<Parada> paradas) {
		List<String> resultado=new ArrayList<>();
		for(Parada p:paradas) {
			String s=p.getId()+": "+p.getNombre()+" ("+p.getRegion()+")";
			resultado.add(s);
		}
		return resultado;
	}

	private void cargarNacionalidades() {
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/paises.xml");
			//String content =new String(inputStream.readAllBytes());
			//File xmlFile=new File(content);
			//InputStreamReader reader = new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8);
//            File xmlFile = new File("src/main/resources/files/paises.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

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
			boolean res=MiAlerta.showConfirmationAlert("¿Quieres insertar los siguientes datos?", mostrarDatosRegistro());
			if(res) {
				registrarPeregrino();
			}
			
		}
	}

	private String mostrarDatosRegistro() {
		String username=txtUsuario.getText();
		String nombre=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		String correo=txtCorreo.getText();
		
		String seleccion = (String) cbParadaInicial.getValue();
		String[]partes = seleccion.split(":"); 
		String id=partes[0];
		Long idParada=Long.parseLong(id);
		
		
		Parada parada=paradaService.find(idParada);	
		String res="Nombre: "+ nombre+"\n"+
				"Usuario: "+ username+"\n"+
				"Correo: "+ correo+"\n"+
				"Nacionalidad: "+ nacionalidad+"\n"+
				"Parada Inicial: "+ parada.getNombre()+" ("+parada.getRegion()+")\n";
				
		return res;
	}

	private void registrarPeregrino() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String nombre=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		String correo=txtCorreo.getText();
		
		String seleccion = (String) cbParadaInicial.getValue();
		String[]partes = seleccion.split(":"); 
		String id=partes[0];
		Long idParada=Long.parseLong(id);
		
		
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
		visitaService.save(visita);
		newPeregrino.exportarCarnetXML();
		String ruta="src/main/resources/files/"+newPeregrino.getUsuario().getUsuario()+"_peregrino.xml";
		File archivo=new File(ruta);
		try {
			ExistDBManager.guardarArchivoEnExistDB(archivo,carnet.getParadaInicial().getNombre().replace(" ", "_")+"_"+carnet.getParadaInicial().getRegion());
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onLimpiar();
		//saveAlert(newPeregrino);
		
		Sesion.getInstancia().setId(newPeregrino.getId());
		Sesion.getInstancia().setNombre(newPeregrino.getNombre());
		Sesion.getInstancia().setTipo("peregrino");
		MiAlerta.showInformationAlert("Peregrino registrado con éxito", "Se ha registrado el peregrino "+newPeregrino.getNombre()+" ("+newPeregrino.getUsuario().getUsuario()+", "+newPeregrino.getUsuario().getEmail()+") en la aplicación."
				+"\nSe ha sellado automáticamente el carnet en la parada inicial, pero si desea una estancia puede solicitarla en la parada.");
		stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
	}

	private boolean camposValidos() {
		String username=txtUsuario.getText();
		String password=txtPassword.getText();
		String password2=txtPassword2.getText();
		String nombrePeregrino=txtNombre.getText();
		String correo=txtCorreo.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		String paradaInicial = (String) cbParadaInicial.getValue();
		
		
		if(!usuarioValido(username)) {
			return false;
		}else if(!passwordValida(password,password2)) {
			return false;
		}else if(!nacionalidadValida(nacionalidad)) {
			return false;
		}else if(!correoValido(correo)) {
			return false;
		}else if(!peregrinoValido(nombrePeregrino)) {
			return false;
		}else if(!paradaValida(paradaInicial)) {
			return false;
		}
		
		return true;
	}

	private boolean paradaValida(String paradaInicial) {
		if(paradaInicial==null || paradaInicial.isBlank() ||paradaInicial.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la selección de parada inicial.", "La parada inicial del peregrino no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}
		
		String[]partes = paradaInicial.split(":"); 
		String id=partes[0];
		Long idParada=Long.parseLong(id);
		Parada parada=paradaService.find(idParada);	
		if(parada==null) {
			MiAlerta.showWarningAlert("Error en la selección de parada inicial.", "Hubo un error con la selección de la parada inicial, vuelva a intentarlo.");
			return false;
		}
		return true;
	}

	private boolean nacionalidadValida(String nacionalidad) {
		if(nacionalidad==null || nacionalidad.isBlank() ||nacionalidad.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la selección de nacionalidad.", "La nacionalidad del peregrino no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}
		return true;
	}

	private boolean peregrinoValido(String responsable) {
		if(responsable==null || responsable.isBlank() ||responsable.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(responsable.length()>50) {
			MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
	        String regex = "^[A-Za-z]+(?: [A-Za-z]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(responsable);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}

	private boolean correoValido(String correo) {
		if(correo==null || correo.isBlank() ||correo.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el correo electrónico", "El correo electrónico no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(correo.length()>50) {
			MiAlerta.showWarningAlert("Error en el correo electrónico", "El nombre de usuario no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
			String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(correo);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el correo electrónico", "El formato del correo electrónico no es válido.");
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

	private boolean passwordValida(String password, String password2) {
		if(password==null || password.isBlank() ||password.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la contraseña", "La contraseña no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(password.length()>50) {
			MiAlerta.showWarningAlert("Error en la contraseña", "La contraseña no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
			for(int i=0;i<password.length();i++) {
				char c=password.charAt(i);
				if(Character.isSpaceChar(c)) {
					MiAlerta.showWarningAlert("Error en la contraseña", "Introdujiste espacios (fallo en la posición "+i+" de la contraseña).");
					return false;
				}
			}
		}
		if(!password.equals(password2)) {
			MiAlerta.showWarningAlert("Error en la contraseña", "Las dos contraseñas introducidas no coinciden.");
			return false;
		}
		return true;
	}

	private boolean usuarioValido(String username) {
		if(username==null || username.isBlank() ||username.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el nombre de usuario", "El nombre de usuario no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(username.length()>50) {
			MiAlerta.showWarningAlert("Error en el nombre de usuario", "El nombre de usuario no puede tener una longitud superior a 50 caracteres.");
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
