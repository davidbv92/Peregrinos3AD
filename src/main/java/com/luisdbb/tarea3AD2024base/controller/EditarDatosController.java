package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
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

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controlador para la edición de los datos del peregrino.
 * Permite modificar el nombre y la nacionalidad del peregrino.
 * 
 * @author David Ballesteros
 * @since 04-02-2025
 */
@Controller
public class EditarDatosController implements Initializable{
	
	@FXML
	private ComboBox<String> cbNacionalidad;
	@FXML
	private TextField txtNombre;
	@FXML
	private Label lblId;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblNacionalidad;
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblCorreo;
	
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnModificar;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private PeregrinoService peregrinoService;
	
	private Peregrino peregrino=new Peregrino();

	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//reset de peregrino
		peregrino=peregrinoService.find(Sesion.getInstancia().getId());
		//cargarNacionalidades
		cargarNacionalidades();
		//cargar labels
		lblId.setText("Id: "+peregrino.getId());
		lblNombre.setText("Nombre: "+peregrino.getNombre());
		lblNacionalidad.setText("Nacionalidad: "+peregrino.getNacionalidad());
		lblUsuario.setText("Usuario: "+peregrino.getUsuario().getUsuario());
		lblCorreo.setText("Correo: "+peregrino.getUsuario().getEmail());
	}

	/**
     * Carga las nacionalidades desde un archivo XML en el ComboBox.
     */
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
	
	/**
     * Maneja el evento de salir de la ventana de edición.
     * Muestra una confirmación antes de volver a la ventana anterior.
     */
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, no se realizará ninguna acción sobre el peregrino.");
		if(res) {
			stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
			
		}
	}
	
	/**
     * Maneja el evento de modificación de los datos del peregrino.
     * Valida los datos y muestra una confirmación antes de realizar la modificación.
     */
	public void onModificar() {
		if(validarDatos()) {
			boolean res=MiAlerta.showConfirmationAlert("¿Quieres modificar los siguientes datos?", mostrarDatosModificacion());
			if(res) {
				modificarPeregrino();
			}
		}
		//MiAlerta.showInformationAlert("Editar datos accionado");
	}

	/**
     * Muestra los datos que se van a modificar.
     *
     * @return Una cadena con los datos a modificar.
     */
	private String mostrarDatosModificacion() {
		String nombre=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		String res="Nombre: "+ nombre+"\n"+
				"Nacionalidad: "+ nacionalidad;
		return res;
	}

	/**
     * Modifica los datos del peregrino y los guarda en la base de datos.
     */
	private void modificarPeregrino() {
		String nombrePeregrino=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		
		peregrino.setNombre(nombrePeregrino);
		peregrino.setNacionalidad(nacionalidad);
		
		Peregrino newPeregrino=peregrinoService.save(peregrino);
		MiAlerta.showInformationAlert("Peregrino modificado con éxito", "Se ha modificado el peregrino "+newPeregrino.getNombre()+" ("+newPeregrino.getUsuario().getUsuario()+", "+newPeregrino.getUsuario().getEmail()+") en la aplicación."); 
		
		stageManager.switchScene(FxmlView.VENTANA_PEREGRINO);
	}

	/**
     * Valida los datos ingresados por el usuario.
     *
     * @return true si los datos son válidos, false en caso contrario.
     */
	private boolean validarDatos() {
		String nombrePeregrino=txtNombre.getText();
		String nacionalidad=(String) cbNacionalidad.getValue();
		if(!nombreValido(nombrePeregrino)) {
			return false;
		}else if(!nacionalidadValida(nacionalidad)) {
			return false;
		}
		return true;
	}

	/**
     * Valida la nacionalidad seleccionada.
     *
     * @param nacionalidad La nacionalidad a validar.
     * @return true si la nacionalidad es válida, false en caso contrario.
     */
	private boolean nacionalidadValida(String nacionalidad) {
		if(nacionalidad==null || nacionalidad.isBlank() ||nacionalidad.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la selección de nacionalidad.", "La nacionalidad del peregrino no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}
		return true;
	}

	/**
     * Valida el nombre del peregrino.
     *
     * @param nombrePeregrino El nombre a validar.
     * @return true si el nombre es válido, false en caso contrario.
     */
	private boolean nombreValido(String nombrePeregrino) {
		if(nombrePeregrino==null || nombrePeregrino.isBlank() ||nombrePeregrino.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(nombrePeregrino.length()>50) {
			MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
	        String regex = "^[A-Za-z]+(?: [A-Za-z]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(nombrePeregrino);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el nombre del peregrino.", "El nombre del peregrino debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}

}
