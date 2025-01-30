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
	
	private Peregrino peregrino=new Peregrino();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
				} 
			} 
		}); 
		
		//listener para activar/desactivar grupoVip
		cbPeregrinos.valueProperty().addListener(new ChangeListener<String>() {

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
		

		
	}


	private void cargarPeregrinos() {
		List<Peregrino> peregrinos=peregrinoService.findAll();
		List<String> datos=convertirPeregrinosString(peregrinos);
		for(String d:datos) {
			cbPeregrinos.getItems().add(d);
		}
		
	}


	private List<String> convertirPeregrinosString(List<Peregrino> peregrinos) {
		List<String> resultado=new ArrayList<>();
		for(Peregrino p:peregrinos) {
			String s=p.getId()+": "+p.getNombre()+" ("+p.getUsuario().getUsuario()+")";
			resultado.add(s);
		}
		return resultado;
	}


	public void onSalir() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas salir?, no se realizará ninguna acción sobre el peregrino.");
		if(res) {
			stageManager.switchScene(FxmlView.VENTANA_PARADA);
			
		}
	}
	
	public void onSellar() {
		boolean quiereEstancia=rdbtnEstanciaSi.isSelected();
		boolean quiereVip=rdbtnVipSi.isSelected();
		if(puedeSellar()) {
			if(puedeEstanciar() && quiereEstancia) {
				//añadir estancia y actualizar carnet y añadir visita
				Estancia estancia=new Estancia();
				estancia.setFecha(LocalDate.now());
				estancia.setParada(parada);
				estancia.setPeregrino(peregrino);
				estancia.setVip(quiereVip);
				Carnet carnet=carnetService.findByPeregrino_Id(peregrino.getId());
				carnet.setDistancia(carnet.getDistancia()+5.0);
				if(quiereVip) {
					carnet.setNvips(carnet.getNvips()+1);
				}
				Visita visita=new Visita();
				visita.setFecha(LocalDate.now());
				visita.setParada(parada);
				visita.setPeregrino(peregrino);
				
				carnetService.save(carnet);
				estanciaService.save(estancia);
				visitaService.save(visita);
			}else if(quiereEstancia && !puedeEstanciar()) {
				MiAlerta.showWarningAlert("No puede estanciarse, ya tiene una estancia en esta parada hoy.");
			}else if(!quiereEstancia) {
				//sellar carnet y añadir visita
				Carnet carnet=carnetService.findByPeregrino_Id(peregrino.getId());
				carnet.setDistancia(carnet.getDistancia()+5.0);
			}
		}else {
			MiAlerta.showWarningAlert("No puede sellar aquí su carnet, ya ha sido sellado hoy en esta parada.");
		}
	}


	private boolean puedeEstanciar() {
		// TODO Auto-generated method stub
		return true;
	}


	private boolean puedeSellar() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
