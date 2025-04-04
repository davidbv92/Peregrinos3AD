package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.SelladoData;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Controlador para la ventana de sellado de carnets.
 * Permite sellar carnets de peregrinos, gestionar estancias y actualizar información de visitas.
 * 
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class VentanaSelladoController implements Initializable{

	@FXML
	private ComboBox<String> cbPeregrinos;
	@FXML
	private RadioButton rdbtnEstanciaSi;
	@FXML
	private RadioButton rdbtnEstanciaNo;
	@FXML
	private RadioButton rdbtnVipSi;
	@FXML
	private RadioButton rdbtnVipNo;
	@FXML
	public ToggleGroup groupEstancia;
	@FXML
	public ToggleGroup groupVip;
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
	private Button btnSellar;
	
	
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
	@Autowired
	private EstanciaService estanciaService;
	
	private Parada parada=new Parada();
	
	Peregrino peregrino=null;
	
	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//reseteo de peregrino y parada
		peregrino=null;
		parada=paradaService.find(Sesion.getInstancia().getId());
		//cargar combobox de peregrinos
		cargarPeregrinos();
		//poner vips desactivados de inicio
		rdbtnVipSi.setDisable(true); 
		rdbtnVipNo.setDisable(true); 
		
		//listener para solo dejar poner vip si quiere estanciarse
		groupEstancia.selectedToggleProperty().addListener(new ChangeListener<Toggle>() { 
			@Override public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) { 
				if (rdbtnEstanciaSi.isSelected()) { 
					rdbtnVipSi.setDisable(false); 
					rdbtnVipNo.setDisable(false); 
				} else { 
					rdbtnVipSi.setDisable(true); 
					rdbtnVipNo.setDisable(true); 
					groupVip.selectToggle(null); 
					rdbtnVipNo.setSelected(true);
				} 
			} 
		}); 
		
		//listener para actualizar valores laterales
		cbPeregrinos.valueProperty().addListener(new ChangeListener<String>() {

			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if(newValue!=null) {
					String[] partes=newValue.split(":");
					String idS=partes[0];
					Long id=Long.parseLong(idS);
					peregrino=peregrinoService.find(id);
					lblId.setText("Id: "+peregrino.getId());
					lblNombre.setText("Nombre: "+peregrino.getNombre());
					lblNacionalidad.setText("Nacionalidad: "+peregrino.getNacionalidad());
					lblUsuario.setText("Usuario: "+peregrino.getUsuario().getUsuario());
					lblCorreo.setText("Correo: "+peregrino.getUsuario().getEmail());
				}
				
			}
			
		});
		
		//poner valores predefinidos a estancia y vip
		rdbtnEstanciaNo.setSelected(true);
		rdbtnVipNo.setSelected(true);
	}

	/**
     * Carga los peregrinos en el ComboBox.
     */
	private void cargarPeregrinos() {
		List<Peregrino> peregrinos=peregrinoService.findAll();
		List<String> datos=convertirPeregrinosString(peregrinos);
		for(String d:datos) {
			cbPeregrinos.getItems().add(d);
		}
		
	}

	/**
     * Convierte una lista de peregrinos en una lista de cadenas para mostrarlas en el ComboBox.
     *
     * @param peregrinos La lista de peregrinos.
     * @return Una lista de cadenas que representan los peregrinos.
     */
	private List<String> convertirPeregrinosString(List<Peregrino> peregrinos) {
		List<String> resultado=new ArrayList<>();
		for(Peregrino p:peregrinos) {
			String s=p.getId()+": "+p.getNombre()+" ("+p.getUsuario().getUsuario()+")";
			resultado.add(s);
		}
		return resultado;
	}

	/**
     * Maneja el evento de salir de la ventana de sellado.
     * Muestra una confirmación antes de volver a la ventana anterior.
     */
	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, no se realizará ninguna acción sobre el peregrino.");
		if(res) {
			stageManager.switchScene(FxmlView.VENTANA_PARADA);
			
		}
	}
	
	/**
     * Maneja el evento de sellar el carnet.
     * Valida los datos y realiza el sellado del carnet o la estancia según corresponda.
     */
	public void onSellar() {
		if(datosValidos()) {
			boolean quiereEstancia=rdbtnEstanciaSi.isSelected();
			boolean quiereVip=rdbtnVipSi.isSelected();
			if(puedeSellar()) {
				if(puedeEstanciar() && quiereEstancia) {
					//añadir estancia y actualizar carnet y añadir visita					
//					MiAlerta.showInformationAlert("Sellado exitoso", "Ha realizado su sellado con éxito en esta parada y se ha registrado su visita. Además, puede disfrutar durante el día de hoy de la estancia en esta parada.");
					boolean res=MiAlerta.showConfirmationAlert("¿Deseas avanzar a la selección de los detalles de la estancias?", "En la siguiente ventana seleccionarás los detalles para complementar la estancia.");
					if(res) {
						SelladoData.getInstancia().setParada(parada);
						SelladoData.getInstancia().setPeregrino(peregrino);
						SelladoData.getInstancia().setVip(quiereVip);
						stageManager.switchScene(FxmlView.DETALLES_ESTANCIA);
					}
					limpiarRadioButtons();
				}else if(quiereEstancia && !puedeEstanciar()) {
					MiAlerta.showWarningAlert("No puede estanciarse, ya tiene una estancia en esta parada hoy.");
				}else if(!quiereEstancia) {
					//sellar carnet y añadir visita
					Carnet carnet=carnetService.findByPeregrino_Id(peregrino.getId());
					carnet.setDistancia(carnet.getDistancia()+5.0);
					Visita visita=new Visita();
					visita.setFecha(LocalDate.now());
					visita.setParada(parada);
					visita.setPeregrino(peregrino);
					carnetService.save(carnet);
					visitaService.save(visita);
					MiAlerta.showInformationAlert("Sellado exitoso", "Ha sellado su carnet con éxito, la visita a esta parada se registró correctamente");
				}
			}else if(puedeEstanciar() && quiereEstancia){
				boolean res=MiAlerta.showConfirmationAlert("¿Deseas avanzar a la selección de los detalles de la estancias?", "En la siguiente ventana seleccionarás los detalles para complementar la estancia.");
				if(res) {
					SelladoData.getInstancia().setParada(parada);
					SelladoData.getInstancia().setPeregrino(peregrino);
					SelladoData.getInstancia().setVip(quiereVip);
					stageManager.switchScene(FxmlView.DETALLES_ESTANCIA);
				}
				limpiarRadioButtons();
//				Estancia estancia=new Estancia();
//				estancia.setFecha(LocalDate.now());
//				estancia.setParada(parada);
//				estancia.setPeregrino(peregrino);
//				estancia.setVip(quiereVip);
//				estanciaService.save(estancia);
//				MiAlerta.showInformationAlert("Estancia registrada correctamente", "Se ha registrado la estancia correctamente, no se ha realizado el sellado puesto que ya ha sellado hoy en esta parada");
			}else {
				MiAlerta.showWarningAlert("No puede sellar aquí su carnet, ya ha sido sellado hoy en esta parada.");
			}
		}else {
			MiAlerta.showWarningAlert("Error en la selección del peregrino", "No ha seleccionado ningún peregrino al que sellar el carnet.");
		}
		
	}

	/**
     * Limpia los botones de radio de estancia y VIP.
     */
	private void limpiarRadioButtons() {
		rdbtnEstanciaNo.setSelected(true);
		
	}

	/**
     * Valida los datos ingresados por el usuario.
     *
     * @return true si los datos son válidos, false en caso contrario.
     */
	boolean datosValidos() {
		if(!peregrinoValido()) {
			return false;
		}else if(!seleccionesValidas()) {
			return false;
		}else {
			return true;
		}
	}

	/**
     * Valida las selecciones de estancia y VIP.
     *
     * @return true si las selecciones son válidas, false en caso contrario.
     */
	boolean seleccionesValidas() {
		if((!rdbtnEstanciaSi.isSelected()) && (!rdbtnEstanciaNo.isSelected())) {
			MiAlerta.showWarningAlert("Error en la estancia", "No ha especificado si quiere estancia o no.");
			return false;
		}else if((!rdbtnVipSi.isSelected()) && (!rdbtnVipNo.isSelected())) {
			MiAlerta.showWarningAlert("Error en la estancia", "No ha especificado si será vip o no.");
			return false;
		}else {
			return true;
		}
	}

	/**
     * Valida que se haya seleccionado un peregrino válido.
     *
     * @return true si el peregrino es válido, false en caso contrario.
     */
	private boolean peregrinoValido() {
		if(peregrino==null) {
			//MiAlerta.showWarningAlert("Error en la selección del peregrino", "Debes seleccionar un peregrino para realizarle el sellado");
			return false;
		}else {
			if(!existePeregrinoBBDD(peregrino)) {
				//MiAlerta.showWarningAlert("Error en la selección del peregrino", "Debes seleccionar un peregrino para realizarle el sellado");
				return false;
			}else {
				return true;
			}
		}
		
	}

	/**
     * Verifica si el peregrino existe en la base de datos.
     *
     * @param p El peregrino a verificar.
     * @return true si el peregrino existe, false en caso contrario.
     */
	private boolean existePeregrinoBBDD(Peregrino p) {
		if(p.getUsuario()!=null) {
			if(p.getUsuario().getUsuario()!=null) {
				return peregrinoService.peregrinoExiste(p);
			}
		}
		
		return false;

	}

	/**
     * Verifica si el peregrino puede estanciarse en la parada actual.
     *
     * @return true si puede estanciarse, false en caso contrario.
     */
	boolean puedeEstanciar() {
		boolean existe=estanciaService.existsByPeregrinoAndParadaAndFecha(peregrino, parada, LocalDate.now());
		if(existe) {
			//MiAlerta.showWarningAlert("Error en la estanciación del peregrino", "El peregrino ya tiene una estancia hoy en esta parada" );
			return false;
		}
		return true;
	}

	/**
     * Verifica si el peregrino puede sellar su carnet en la parada actual.
     *
     * @return true si puede sellar, false en caso contrario.
     */
	boolean puedeSellar() {
		boolean existe=visitaService.existsByPeregrinoAndParadaAndFecha(peregrino, parada, LocalDate.now());
		if(existe) {
			//MiAlerta.showWarningAlert("Error en el sellado del carnet", "El peregrino ya ha sido sellado hoy en esta parada" );
			return false;
		}
		return true;
	}
	
}
