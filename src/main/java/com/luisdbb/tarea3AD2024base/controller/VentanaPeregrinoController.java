package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
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
import com.luisdbb.tarea3AD2024base.modelo.ReportData;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Controlador para la ventana del peregrino.
 * Permite gestionar la visualización de datos, modificación de datos, exportación del carnet y acceso a la ayuda.
 * 
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class VentanaPeregrinoController implements Initializable{

	
	@FXML
	private MenuItem itemMostrarDatos;
	@FXML
	private MenuItem itemModificarDatos;
	@FXML
	private MenuItem itemExportarCarnet;
	@FXML
	private MenuItem itemAyuda;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblCorreo;
	
	@FXML
	private Button btnMostrarDatos;
	@FXML
	private Button btnModificarDatos;
	@FXML
	private Button btnExportarCarnet;
	@FXML
	private Button btnCerrarSesion;

	private Peregrino peregrino;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private VisitaService visitaService;
	@Autowired
	private EstanciaService estanciaService;
	@Autowired
	private CarnetService carnetService;
	
	/**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se conoce.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//poner shortcuts
		itemMostrarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		itemModificarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
		itemExportarCarnet.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		//objeto peregrino actual
		peregrino=peregrinoService.find(Sesion.getInstancia().getId());
		//personalizar según sesión
		lblTitulo.setText("BIENVENIDO, "+peregrino.getUsuario().getUsuario().toUpperCase());
		lblNombre.setText("Nombre: "+peregrino.getNombre());
		lblUsuario.setText("Usuario: "+peregrino.getUsuario().getUsuario());
		lblCorreo.setText("Correo: "+peregrino.getUsuario().getEmail());
	}
	
	/**
     * Maneja el evento de cerrar sesión.
     * Muestra una confirmación antes de cerrar la sesión y volver a la pantalla de inicio de sesión.
     */
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			Sesion.getInstancia().setId(0L);
			Sesion.getInstancia().setNombre("Invitado");
			Sesion.getInstancia().setTipo("Invitado");
			stageManager.switchScene(FxmlView.LOGIN);
		}
		
	}
	
	/**
     * Maneja el evento de exportar el carnet.
     * Exporta el carnet del peregrino a un archivo XML.
     */
	public void onExportarCarnet() {
		List<Visita>visitas=visitaService.findByPeregrino_Id(peregrino.getId());
		List<Parada> paradas=new ArrayList<>();
		List<Estancia> estancias=new ArrayList<>();
		for(Visita v:visitas) {
			Parada p=paradaService.find(v.getParada().getId());
			paradas.add(p);
		}
		estancias=estanciaService.findByPeregrino(peregrino);
		peregrino.setParadas(paradas);
		peregrino.setEstancias(estancias);
		//Carnet c=carnetService.findByPeregrino_Id(peregrino.getId());
		peregrino.exportarCarnetXML();
		MiAlerta.showInformationAlert("Exportación exitosa","Puede ver su carnet en: src/main/resources/files/"+peregrino.getUsuario().getUsuario()+"_peregrino.xml");
	}
	
	/**
     * Maneja el evento de mostrar los datos del peregrino.
     * Genera un informe PDF con los datos del peregrino.
     */
	public void onMostrarDatos() {
		Long idPer=Sesion.getInstancia().getId();
		Peregrino peregrino=peregrinoService.find(idPer);
		Carnet carnet=carnetService.findByPeregrino_Id(idPer);
		
		
		ReportData rd=new ReportData();
		Long id=carnet.getId();
		Double distancia=carnet.getDistancia();
		Long nvips=(long) carnet.getNvips();
		String parada_inicial=carnet.getParadaInicial().getNombre()+ " ("+carnet.getParadaInicial().getRegion()+")";
		String usuario=peregrino.getUsuario().getUsuario();
		String nacionalidad=peregrino.getNacionalidad();
		String nombre_completo=peregrino.getNombre();
		String correo=peregrino.getUsuario().getEmail();
		String imagen="src/main/resources/images/conchaBlanco.png";
		
		rd.setCorreo(correo);
		rd.setDistancia(distancia);
		rd.setId(id);
		rd.setNacionalidad(nacionalidad);
		rd.setNombre_completo(nombre_completo);
		rd.setNvips(nvips);
		rd.setParada_inicial(parada_inicial);
		rd.setUsuario(usuario);
		rd.setImagen(imagen);
		
		List<ReportData> lista=new ArrayList<>();
		lista.add(rd);
		String ruta="src/main/resources/informes/"+usuario+"_carnet.pdf";
		//String ruta = "C:\\Users\\alumnoDAM\\Downloads\\Peregrinos3AD-main\\Peregrinos3AD-main\\src\\main\\resources\\informes\\resultadoPrueba.pdf";
		//reportController.generateCarnetReport(lista, ruta);
		generarCarnetReport(lista,ruta);
	}
	
	/**
     * Maneja el evento de modificar los datos del peregrino.
     * Redirige a la pantalla de edición de datos.
     */
	public void onModificarDatos() {
		stageManager.switchScene(FxmlView.EDITAR_DATOS);
	}
	
	/**
     * Maneja el evento de mostrar la ayuda.
     * Abre una ventana modal con la documentación de ayuda.
     */
	public void onAyuda() {
		//MiAlerta.showInformationAlert("Información accionado");
		mostrarAyuda();
		
	}
	
	/**
     * Genera un informe PDF del carnet del peregrino.
     *
     * @param listaReportData La lista de datos del carnet.
     * @param rutaSalida      La ruta donde se guardará el archivo PDF.
     */
	private void generarCarnetReport(List<ReportData> listaReportData, String rutaSalida) {
		try {
            //cargar el Jasper
			String jasperFile = "src/main/resources/informes/carnetPeregrino.jasper";
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFile);
            //preparar el datasource con los datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(listaReportData);
            //rellenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            //exportar el reporte a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);
            //solicitar confirmación para mostrar o no el carnet
            boolean res=MiAlerta.showConfirmationAlert("Carnet generado con éxito", "Se ha generado su carnet exitosamente, lo podrá encontrar en la ruta "+rutaSalida+"."
            		+ "\n¿Desea visializar su carnet en este momento?");
            
            if(res) {
            	stageManager.openPdfInModal(rutaSalida);
            }

        } catch (JRException e) {
            e.printStackTrace();
        }
		
	}

	/**
     * Muestra la ventana de ayuda con la documentación HTML.
     */
	private void mostrarAyuda() {
		WebView webView=new WebView();
		String url=getClass().getResource("/help/ventanaPeregrinoHelp.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage=new Stage();
		helpStage.setTitle("Ayuda PEREGRINAPP");
		
		Scene helpScene=new Scene(webView,600,400);
		
		helpStage.setScene(helpScene);
		
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(true);
		helpStage.show();
	}

}
