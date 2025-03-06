package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class VentanaParadaController implements Initializable {

	@FXML
	private MenuItem itemMostrarDatos;
	@FXML
	private MenuItem itemExportarDatos;
	@FXML
	private MenuItem itemSellarCarnet;
	@FXML
	private MenuItem itemAyuda;
	@FXML
	private MenuItem itemSalir;
	
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblRegion;
	@FXML
	private Label lblResponsable;
	
	
	@FXML
	private Button btnMostrarDatos;
	@FXML
	private Button btnSellarCarnet;
	@FXML
	private Button btnGenerarInforme;
	@FXML
	private Button btnVerEnvios;
	@FXML
	private Button btnCerrarSesion;
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private ParadaService paradaService;
	@Autowired
    private VisitaService visitaService;
	@Autowired
    private EstanciaService estanciaService;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//poner shortcuts
		itemMostrarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		itemExportarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		itemSellarCarnet.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		itemAyuda.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		Parada parada=paradaService.find(Sesion.getInstancia().getId());
		//personalizar según sesión
		lblTitulo.setText("BIENVENIDO, "+Sesion.getInstancia().getNombre().toUpperCase());
		lblNombre.setText("Nombre: "+parada.getNombre());
		lblRegion.setText("Región: "+parada.getRegion());
		lblResponsable.setText("Responsable: "+parada.getResponsable());
		
	}

	
	public void onCerrarSesion() {
		boolean res=MiAlerta.showConfirmationAlert("¿Estás seguro de que deseas cerrar tu sesión para volver a la ventana de inicio de sesión?");
		if(res) {
			Sesion.getInstancia().setId(0L);
			Sesion.getInstancia().setNombre("Invitado");
			Sesion.getInstancia().setTipo("Invitado");
			stageManager.switchScene(FxmlView.LOGIN);
		}
	}
	
	public void onSellarCarnet() {
		//MiAlerta.showInformationAlert("Sellar Carnet accionado");
		stageManager.switchScene(FxmlView.VENTANA_SELLADO);
	}
	
	public void onMostrarDatos() {
		//MiAlerta.showInformationAlert("Mostrar datos accionado");
		stageManager.openModal(FxmlView.DATOS_PARADA);
	}
	
	public void onGenerarInforme() {
		//MiAlerta.showInformationAlert("Esta funcionalidad no está disponible en esta versión.");
		generarInforme();
	}
	
	public void onAyuda() {
		//MiAlerta.showInformationAlert("Información accionado");
		mostrarAyuda();
	}
	
	private void mostrarAyuda() {
		WebView webView=new WebView();
		String url=getClass().getResource("/help/ventanaParadaHelp.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage=new Stage();
		helpStage.setTitle("Ayuda PEREGRINAPP");
		
		Scene helpScene=new Scene(webView,600,400);
		
		helpStage.setScene(helpScene);
		
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(true);
		helpStage.show();
	}
	
	public void onVerEnvios() {
		stageManager.openModal(FxmlView.ENVIOS_REALIZADOS);
	}
	
	
	public void generarInforme() {
	    Parada parada = paradaService.find(Sesion.getInstancia().getId());
	    try {
	        JasperDesign jasperDesign = new JasperDesign();
	        jasperDesign.setName("Estadisticas " + parada.getNombre() + "_" + parada.getRegion());
	        jasperDesign.setPageWidth(595);
	        jasperDesign.setPageHeight(842);
	        jasperDesign.setColumnWidth(515);
	        jasperDesign.setColumnSpacing(0);
	        jasperDesign.setLeftMargin(40);
	        jasperDesign.setRightMargin(40);
	        jasperDesign.setTopMargin(50);
	        jasperDesign.setBottomMargin(50);

	        // Parámetros
	        JRDesignParameter paramInfo = new JRDesignParameter();
	        paramInfo.setName("info");
	        paramInfo.setValueClass(String.class);
	        jasperDesign.addParameter(paramInfo);
	        
	        JRDesignParameter paramTotalPeregrinos = new JRDesignParameter();
	        paramTotalPeregrinos.setName("totalPeregrinos");
	        paramTotalPeregrinos.setValueClass(Integer.class);
	        jasperDesign.addParameter(paramTotalPeregrinos);

	        JRDesignParameter paramTotalEstancias = new JRDesignParameter();
	        paramTotalEstancias.setName("totalEstancias");
	        paramTotalEstancias.setValueClass(Integer.class);
	        jasperDesign.addParameter(paramTotalEstancias);

	        // Banda de detalles
	        JRDesignBand detailBand = new JRDesignBand();
	        detailBand.setHeight(100);

	        JRDesignStaticText text0 = new JRDesignStaticText();
	        text0.setText("Información de la parada:");
	        text0.setX(20);
	        text0.setY(0);
	        text0.setWidth(200);
	        text0.setHeight(20);
	        detailBand.addElement(text0);
	        
	        JRDesignStaticText text1 = new JRDesignStaticText();
	        text1.setText("Total de Peregrinos:");
	        text1.setX(20);
	        text1.setY(30);
	        text1.setWidth(200);
	        text1.setHeight(20);
	        detailBand.addElement(text1);

	        JRDesignStaticText text2 = new JRDesignStaticText();
	        text2.setText("Total de Estancias:");
	        text2.setX(20);
	        text2.setY(60);
	        text2.setWidth(200);
	        text2.setHeight(20);
	        detailBand.addElement(text2);

	        JRDesignTextField textFieldInfo = new JRDesignTextField();
	        textFieldInfo.setExpression(new JRDesignExpression("$P{info}"));
	        textFieldInfo.setX(230);
	        textFieldInfo.setY(0);
	        textFieldInfo.setWidth(250);
	        textFieldInfo.setHeight(20);
	        detailBand.addElement(textFieldInfo);
	        
	        JRDesignTextField textFieldPeregrinos = new JRDesignTextField();
	        textFieldPeregrinos.setExpression(new JRDesignExpression("$P{totalPeregrinos}"));
	        textFieldPeregrinos.setX(230);
	        textFieldPeregrinos.setY(30);
	        textFieldPeregrinos.setWidth(250);
	        textFieldPeregrinos.setHeight(20);
	        detailBand.addElement(textFieldPeregrinos);

	        JRDesignTextField textFieldEstancias = new JRDesignTextField();
	        textFieldEstancias.setExpression(new JRDesignExpression("$P{totalEstancias}"));
	        textFieldEstancias.setX(230);
	        textFieldEstancias.setY(60);
	        textFieldEstancias.setWidth(250);
	        textFieldEstancias.setHeight(20);
	        detailBand.addElement(textFieldEstancias);

	        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

	        // Obtener datos
	        String info=mapearParada(parada);
	        
	        List<Visita> visitas = visitaService.findByParada_Id(parada.getId());
	        Set<Long> peregrinos = new HashSet<>();
	        for (Visita v : visitas) {
	            peregrinos.add(v.getPeregrino().getId());
	        }
	        Integer totalPeregrinos = peregrinos.size();
	        Integer totalEstancias = estanciaService.findByParada(parada).size();

	        // Parámetros
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("info", info);
	        parameters.put("totalPeregrinos", totalPeregrinos);
	        parameters.put("totalEstancias", totalEstancias);

	        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        String outputFile = "src/main/resources/informes/Estadisticas_" + 
	            parada.getNombre()+"_"+ 
	            parada.getRegion()+".pdf";

	        JasperExportManager.exportReportToPdfFile(print, outputFile);
	        
	        boolean res=MiAlerta.showConfirmationAlert("Informe generado con éxito", "Se ha generado su informe exitosamente, lo podrá encontrar en la ruta "+outputFile+"."
            		+ "\n¿Desea visializar su informe en este momento?");
            
            if(res) {
            	stageManager.openPdfInModal(outputFile);
            }
	        System.out.println("Reporte generado: " + outputFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	private String mapearParada(Parada parada) {
		return parada.getNombre()+" ("+parada.getRegion()+"), gestionada por "+parada.getResponsable();
	}

}
