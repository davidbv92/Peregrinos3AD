package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ConjuntoContratadoService;
import com.luisdbb.tarea3AD2024base.services.EnvioACasaService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author David Ballesteros
 * @since 24-02-2025
 */
@Controller
public class DetallesEstanciaController implements Initializable{

	@FXML
	private VBox panelServicios;
	@FXML
	private GridPane panelEnvio;
	
	@FXML
	private ComboBox<String> cbPago;
	@FXML
	private TextField txtExtra;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtLocalidad;
	@FXML
	private TextField txtPeso;
	@FXML
	private TextField txtAlto;
	@FXML
	private TextField txtAncho;
	@FXML
	private TextField txtProfundo;
	@FXML
	private CheckBox checkUrgente;
	
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnSellar;
	
	private List<Servicio> listaServicios=new ArrayList<>(0);
	private final List<CheckBox> checkBoxes = new ArrayList<>(0); 
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private ServicioService servicioService;
	@Autowired
    private ConjuntoContratadoService conjuntoContratadoService;
	@Autowired
    private EnvioACasaService envioACasaService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		panelEnvio.setDisable(true);
		listaServicios=servicioService.findAllByParadaId(Sesion.getInstancia().getId());
		
		//cargar elementos
		cargarServicios();
		cargarComboPago();
		
		//listeenr para envio a casa
		int posicion=buscarEnvioACasa(listaServicios);
		if(posicion!=-1 && !checkBoxes.isEmpty()) {
			checkBoxes.get(posicion).selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                panelEnvio.setDisable(!isSelected);
            });
		}
		
	}
	
	private void cargarComboPago() {
		cbPago.getItems().add("Efectivo");
		cbPago.getItems().add("Tarjeta");
		cbPago.getItems().add("Bizum");
		cbPago.setValue("Efectivo");
		
	}

	private int buscarEnvioACasa(List<Servicio> listaServicios2) {
		for(int i=0;i<listaServicios2.size();i++) {
			Servicio s=listaServicios2.get(i);
			if(s.getNombre().equals("Envío a Casa")) {
				return i;
			}
		}
		return -1;
	}

	private void cargarServicios() {
		for (Servicio s : listaServicios) {
			String cadena=s.formatoCadena();
            CheckBox checkBox = new CheckBox(cadena);
            checkBoxes.add(checkBox); // Guardamos cada CheckBox en la lista
            panelServicios.getChildren().add(checkBox);
        }
		
	}

	public void onSellar() {
		if(camposValidos()) {
			String pago=cbPago.getValue();
			String extra=txtExtra.getText();
			List<Long> seleccionados = new ArrayList<>();
	        for (int i = 0; i < listaServicios.size(); i++) {
	            if (checkBoxes.get(i).isSelected()) {
	                seleccionados.add(listaServicios.get(i).getId());
	            }
	        }
	        
	        Long id=conjuntoContratadoService.calcularIdMaximo()+1;
	        double total=calcularTotal(listaServicios);
	        char pagoChar=pago.charAt(0);
	        Long idEstancia=null;
	        ConjuntoContratado c=new ConjuntoContratado(id,total,pagoChar,extra,idEstancia,seleccionados);
			conjuntoContratadoService.save(null);
	        
			if(!panelEnvio.isDisabled()) {
				String direccion=txtDireccion.getText();
				String localidad=txtLocalidad.getText();
				String peso=txtPeso.getText();
				String alto=txtAlto.getText();
				String ancho=txtAncho.getText();
				String profundo=txtProfundo.getText();
				boolean urgente=checkUrgente.isSelected();
			}
		}
//		List<Servicio> seleccionados = new ArrayList<>();
//        for (int i = 0; i < listaServicios.size(); i++) {
//            if (checkBoxes.get(i).isSelected()) {
//                seleccionados.add(listaServicios.get(i));
//            }
//        }
//        
//        MiAlerta.showInformationAlert(mostrarSeleccionados(seleccionados));
	}
	
	private double calcularTotal(List<Servicio> listaServicios2) {
		double total=0L;
		for(Servicio s:listaServicios2) {
			total=total+s.getPrecio();
		}
		return total;
	}

	private boolean camposValidos() {
		String pago=cbPago.getValue();
		String extra=txtExtra.getText();
		
		if(!pagoValido(pago)) {
			return false;
		}
		if(!extraValido(extra)) {
			return false;
		}
		if(!panelEnvio.isDisabled()) {
			//MiAlerta.showInformationAlert("LosRecojo");
			String direccion=txtDireccion.getText();
			if(!direccionValida(direccion)) {
				return false;
			}
			String localidad=txtLocalidad.getText();
			if(!localidadValida(localidad)) {
				return false;
			}
			String peso=txtPeso.getText();
			if(!pesoValido(peso)) {
				return false;
			}
			String alto=txtAlto.getText();
			String ancho=txtAncho.getText();
			String profundo=txtProfundo.getText();
			if(!medidasValidas(alto,ancho,profundo)) {
				return false;
			}
			boolean urgente=checkUrgente.isSelected();
		}else {
			//MiAlerta.showInformationAlert("NoLosRecojo");
		}
		
		
		return true;
	}

	private boolean medidasValidas(String alto, String ancho, String profundo) {
		try {
	        double altura = Double.parseDouble(alto);
	        double anchoMedida = Double.parseDouble(ancho);
	        double profundidad = Double.parseDouble(profundo);

	        if (altura > 0 && anchoMedida > 0 && profundidad > 0) {
	            return true;
	        } else {
	        	MiAlerta.showWarningAlert("Error en las medidas", "Las medidas no pueden estar vacías ni ser solo espacios en blanco.");
	            return false;  
	        }
	    } catch (NumberFormatException e) {
	    	MiAlerta.showWarningAlert("Error en las medidas", "No introdujiste medidas en formato válido. Deberán tener el siguiente formato X(.XX).");
	        return false;
	    }
	}

	private boolean pesoValido(String peso) {
		if(peso==null || peso.isBlank() ||peso.isEmpty()) {
			MiAlerta.showWarningAlert("Error en el peso", "El peso no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(peso.length()>9) {
			MiAlerta.showWarningAlert("Error en el peso", "El peso no puede ser tan elevado.");
			return false;
		}else {
			String regex = "^\\d+(\\.\\d{1,2})?$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(peso);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en el peso.", "El peso debe ser positivo y seguir el formato X(.XX) de parte entera y opcionalmente parte decimal separada por un (.) y de hasta dos decimales.");
				return false;
	        }
		}
		return true;
	}

	private boolean localidadValida(String localidad) {
		if(localidad==null || localidad.isBlank() ||localidad.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la localidad", "La localidad no puede estar vacío ni ser solo espacios en blanco.");
			return false;
		}else if(localidad.length()>50) {
			MiAlerta.showWarningAlert("Error en la localidad", "La localidad no puede tener una longitud superior a 50 caracteres.");
			return false;
		}else {
			String regex = "^[\\p{L}]+(?: [\\p{L}]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(localidad);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en la localidad.", "La localidad debe comenzar y terminar por letra y no tener más de un espacio seguido. Además, solo puede contener letras y espacios.");
				return false;
	        }
		}
		return true;
	}

	private boolean direccionValida(String direccion) {
		if(direccion==null || direccion.isBlank() ||direccion.isEmpty()) {
			MiAlerta.showWarningAlert("Error en la direccion", "La direccion no puede estar vacía ni ser solo espacios en blanco.");
			return false;
		}else if(direccion.length()>100) {
			MiAlerta.showWarningAlert("Error en la direccion", "La direccion no puede tener una longitud superior a 100 caracteres.");
			return false;
		}else {
			String regex = "^[A-Za-z0-9\\s,.-]+(\\s[A-Za-z0-9\\s,.-]+)*$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(direccion);
	        if(!matcher.matches()) {
	        	MiAlerta.showWarningAlert("Error en la direccion.", "La dirección no cumple con los requisitos internacionales. Solo se pueden usar letras, números, espacios, puntos, comas y guiones.");
				return false;
	        }
		}
		return false;
	}

	private boolean extraValido(String extra) {
		if(extra==null || extra.isBlank() ||extra.isEmpty()) {
			extra="";
			return true;
		}else if(extra.length()>250) {
			MiAlerta.showWarningAlert("Error en los extras","Los extras no pueden tener una longitud superior a 250 caracteres.");
			return false;
		}
		return true;
	}

	private boolean pagoValido(String pago) {
		List<String> pagos=new ArrayList<>();
		for(String s:cbPago.getItems()) {
			pagos.add(s);
		}
		return pagos.contains(pago);
	}

	private String mostrarSeleccionados(List<Servicio> seleccionados) {
		String res="";
		for(Servicio s:seleccionados) {
			res=res+s.toString()+"\n";
		}
		return res;
	}

	public void onLimpiar() {
		
	}
	
	public void onVolver() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas volver?, no se guardarán los datos.");
		if(res) {
			stageManager.switchScene(FxmlView.VENTANA_PARADA);
			
		}
	}

}
