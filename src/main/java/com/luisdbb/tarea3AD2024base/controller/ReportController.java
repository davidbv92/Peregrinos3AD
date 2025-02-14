package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ReportData;

import javafx.fxml.Initializable;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


/**
 * @author David Ballesteros
 * @since 23-01-2025
 */
@Controller
public class ReportController implements Initializable{
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public void generateCarnetReport(List<ReportData> reportDataList, String outputPath) {
        try {
            // Cargar el archivo Jasper compilado
        	String jasperFile = "C:\\Users\\alumnoDAM\\Downloads\\Peregrinos3AD-main\\Peregrinos3AD-main\\src\\main\\resources\\informes\\carnetPeregrinoPrueba.jasper";
            //String jasperFile = "/informes/carnetPeregrinoPersonalizado.jasper";  // Ruta a tu archivo .jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFile);

            // Preparar el datasource con los datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(reportDataList);

            // Llenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Exportar el reporte a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            
            //JasperViewer.viewReport(jasperPrint, false);
            stageManager.openPdfInModal(outputPath);

            System.out.println("Carnet generado exitosamente: " + outputPath);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	
}
