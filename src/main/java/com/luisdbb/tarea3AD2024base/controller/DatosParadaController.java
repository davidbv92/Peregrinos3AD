package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class DatosParadaController implements Initializable{

	@FXML
	private DatePicker fechaInicio;
	@FXML
	private DatePicker fechaFin;
	@FXML
	private TextField mostrarFechaInicio;
	@FXML
	private TextField mostrarFechaFin;
	@FXML
	private Button btnBuscar;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtRegion;
	
	@FXML
    private TableView<Estancia> tableView;
    @FXML
    private TableColumn<Estancia, Long> colId;
    @FXML
    private TableColumn<Estancia, String> colPeregrino;
    @FXML
    private TableColumn<Estancia, LocalDate> colFecha;
    @FXML
    private TableColumn<Estancia, Boolean> colVip;
	
    @Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private EstanciaService estanciaService;
	
	private ObservableList<Estancia> listaEstancias = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//preparar tabla
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPeregrino.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getPeregrino().getNombre())
    );
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colVip.setCellValueFactory(new PropertyValueFactory<>("vip"));
		
        
	}
	
	
	public void onBuscar() {
		if(validarFechas()) {
			Parada parada=paradaService.find(Sesion.getInstancia().getId());
			LocalDate fechaInicioSeleccionada=fechaInicio.getValue();
			LocalDate fechaFinSeleccionada=fechaFin.getValue();
			List<Estancia>estancias=estanciaService.findByFechaBetweenAndParada(fechaInicioSeleccionada,fechaFinSeleccionada,parada);
			listaEstancias=FXCollections.observableArrayList(estancias);
			tableView.setItems(listaEstancias);
			txtId.setText(parada.getId().toString());
			txtNombre.setText(parada.getNombre());
			txtRegion.setText(parada.getRegion()+"");
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			mostrarFechaInicio.setText(fechaInicioSeleccionada.format(formato));
			mostrarFechaFin.setText(fechaFinSeleccionada.format(formato));
		}
		
	}


	private boolean validarFechas() {
		LocalDate fechaInicioSeleccionada=fechaInicio.getValue();
		LocalDate fechaFinSeleccionada=fechaFin.getValue();
		if(fechaInicioSeleccionada==null) {
			MiAlerta.showErrorAlert("La fecha de inicio introducida no puede ser nula." );
			return false;
		}
		if(fechaFinSeleccionada==null) {
			MiAlerta.showErrorAlert("La fecha de fin introducida no puede ser nula." );
			return false;
		}
		if(fechaInicioSeleccionada.isAfter(LocalDate.now())) {
			MiAlerta.showErrorAlert("La fecha de inicio introducida es posterior a la fecha actual. No se permiten valores posteriores a la fecha actual." );
			return false;
		}else if(fechaFinSeleccionada.isAfter(LocalDate.now())) {
			MiAlerta.showErrorAlert("La fecha de fin introducida es posterior a la fecha actual. No se permiten valores posteriores a la fecha actual." );
			return false;
		}else if(fechaInicioSeleccionada.isAfter(fechaFinSeleccionada)) {
			MiAlerta.showErrorAlert("La fecha de inicio introducida es posterior a la fecha de fin. No se permiten esos valores." );
			return false;
		}else {
			return true;
		}
	}

}
