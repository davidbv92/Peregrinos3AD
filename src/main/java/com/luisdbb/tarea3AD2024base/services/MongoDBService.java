package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Visita;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class MongoDBService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Transactional
	public void generarBackup() {
		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String titulo = "BACKUP DE CARNETS (" +fecha+ ")";
		Document documentoPeregrinos = new Document();
		
		documentoPeregrinos.append("Título", titulo);
		
		List<Peregrino> peregrinos = peregrinoRepository.findAll();
	    List <Document> documentosPeregrinos = new ArrayList<>();
	    
	    for (Peregrino p: peregrinos) {
	    	Hibernate.initialize(p.getEstancias());
	    	Hibernate.initialize(p.getParadas());
	    	Document datosPeregrino = new Document();
	    	datosPeregrino.append("ID", p.getId());
	    	datosPeregrino.append("Nombre", p.getNombre());
	    	datosPeregrino.append("Nacionalidad", p.getNacionalidad());
	    	datosPeregrino.append("Expedido en", p.getCarnet().getParadaInicial().getNombre());
	    	datosPeregrino.append("Fecha de expedición", p.getCarnet().getFechaexp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    	datosPeregrino.append("Distancia recorrida", p.getCarnet().getDistancia());
	    	datosPeregrino.append("Número de vips", p.getCarnet().getNvips());
	    	
	    	List <Document> listaParadas = new ArrayList<>();
	    	for (Visita v: p.getVisitas()) {
	    		Parada pa=v.getParada();
	    		Document datosPara = new Document();
	    		datosPara.append("Nombre", pa.getNombre());
	    		datosPara.append("Región", pa.getRegion());
	    		listaParadas.add(datosPara);
	    	}
	    	
	    	List <Document> listaEstancias = new ArrayList<>();
	    	for(Estancia estancia:p.getEstancias()) {
	    		Document datosEstancia=new Document();
	    		datosEstancia.append("ID", estancia.getId());
	    		datosEstancia.append("Parada", estancia.getParada().getNombre());
	    		datosEstancia.append("Fecha", estancia.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    		datosEstancia.append("Vip", estancia.isVip());
	    		listaEstancias.add(datosEstancia);
	    	}
	    	
	    	datosPeregrino.append("Lista de Paradas",listaParadas);
	    	datosPeregrino.append("Lista de Estancias",listaEstancias);
	    	documentosPeregrinos.add(datosPeregrino);
	    }
	    
	    documentoPeregrinos.append("Peregrino", documentosPeregrinos);
	    String nombreBackup = "Backup_Carnets_"+fecha;
	    mongoTemplate.getCollection(nombreBackup).insertOne(documentoPeregrinos);
	    MiAlerta.showInformationAlert("Backup terminado.", "Se ha realizado un backup exitoso de los datos de los peregrinos la aplicación en MongoDB.");
		
	}
}
