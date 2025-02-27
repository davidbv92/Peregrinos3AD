package com.luisdbb.tarea3AD2024base.modelo;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * @author David Ballesteros
 * @since 08-01-2025
 */

@Entity
@Table(name = "Peregrino")
public class Peregrino implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String nombre;
	
	private String nacionalidad;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private User usuario;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
	private Carnet carnet;
	
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<Visita> visitas=new ArrayList<Visita>();
	
	@OneToMany(mappedBy = "peregrino")
	private List<Estancia> estancias=new ArrayList<Estancia>();
	
	@Transient
	private List<Parada> paradas=new ArrayList<Parada>();
	
	//constructores
//	public Peregrino(Long id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial,List<Parada> paradas,List<Estancia> estancias) {
//		super();
//		this.id = id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}
//	
//	public Peregrino(Long id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial) {
//		super();
//		this.id = id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas.add(paradaInicial);
//	}
	
	public Peregrino() {
		
	}


//	public Peregrino(int id, String nombre, String nacionalidad, Long idCarnet, Parada paradaInicial,
//			List<Parada> paradas, List<Estancia> estancias) {
//		super();
//		this.id = (long) id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = new Carnet(idCarnet, paradaInicial);
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}

//	public Peregrino(int id, String nombre, String nacionalidad, Carnet carnet, List<Parada> paradas,
//			List<Estancia> estancias) {
//		super();
//		this.id = (long) id;
//		this.nombre = nombre;
//		this.nacionalidad = nacionalidad;
//		this.carnet = carnet;
//		this.paradas = paradas;
//		this.estancias=estancias;
//	}
	
	//getter y setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}


	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", usuario=" + usuario
				+ ", carnet=" + carnet + ", paradas=" + paradas + ", estancias=" + estancias + "]";
	}


	public void exportarCarnetXML() {
		List<Parada> paradasXML=new ArrayList<Parada>();
		 List<Estancia> estanciasXML=new ArrayList<Estancia>();
		 
		 for(Parada p:paradas) {
			 paradasXML.add(p);
		 }
		 for(Estancia e:estancias) {
			 estanciasXML.add(e);
		 }
		try {
           
           DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
           DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
           DOMImplementation implementacion = constructorDocumento.getDOMImplementation();

           
           Document documento = implementacion.createDocument(null,"carnet", null);
           Element carnet = documento.getDocumentElement();

           
//           documento.setXmlVersion("1.0");
//           ProcessingInstruction ip = documento.createProcessingInstruction("xml-stylesheet", "type=\"text/xml\" href=\"test.xsl\"");
//           documento.insertBefore(ip, coches);

           Element id,fechaexp,expedidoen,peregrino,nombre,nacionalidad,hoy,distanciaTotal,paradas,parada,orden,nombreParada,region,estancias,estancia,idEstancia,fecha,paradaEs,vip,vips;
           Text idV,fechaexpV,expedidoenV,nombreV,nacionalidadV,hoyV,distanciaTotalV,ordenV,nombreParadaV,regionV,idEstanciaV,fechaV,paradaEsV,vipsV;

           id = documento.createElement("id");
           carnet.appendChild(id);
           idV=documento.createTextNode(getId().toString());
           id.appendChild(idV);
           
           fechaexp = documento.createElement("fechaexp");
           carnet.appendChild(fechaexp);
           fechaexpV=documento.createTextNode(getCarnet().getFechaexp().toString());
           fechaexp.appendChild(fechaexpV);
           
           expedidoen = documento.createElement("expedidoen");
           carnet.appendChild(expedidoen);
           expedidoenV=documento.createTextNode(getCarnet().getParadaInicial().getNombre());
           expedidoen.appendChild(expedidoenV);
           
           peregrino=documento.createElement("peregrino");
           carnet.appendChild(peregrino);
           
           nombre=documento.createElement("nombre");
           peregrino.appendChild(nombre);
           nombreV=documento.createTextNode(getNombre());
           nombre.appendChild(nombreV);
           
           nacionalidad=documento.createElement("nacionalidad");
           peregrino.appendChild(nacionalidad);
           nacionalidadV=documento.createTextNode(getNacionalidad());
           nacionalidad.appendChild(nacionalidadV);
           
           hoy=documento.createElement("hoy");
           carnet.appendChild(hoy);
           hoyV=documento.createTextNode(LocalDate.now().toString());
           hoy.appendChild(hoyV);
           
           distanciaTotal=documento.createElement("distanciaTotal");
           carnet.appendChild(distanciaTotal);
           distanciaTotalV=documento.createTextNode(""+getCarnet().getDistancia());
           distanciaTotal.appendChild(distanciaTotalV);
           
           paradas=documento.createElement("paradas");
           carnet.appendChild(paradas);
           
           int ord=1;
           
           for(Parada p:paradasXML) {
           	parada= documento.createElement("parada");
           	paradas.appendChild(parada);
           	
           	orden = documento.createElement("orden");
           	parada.appendChild(orden);
           	ordenV=documento.createTextNode(ord+"");
           	ord=ord+1;
           	orden.appendChild(ordenV);
           	
           	nombreParada = documento.createElement("nombre");
           	parada.appendChild(nombreParada);
           	nombreParadaV=documento.createTextNode(p.getNombre());
           	nombreParada.appendChild(nombreParadaV);
           	
           	region = documento.createElement("region");
           	parada.appendChild(region);
           	regionV=documento.createTextNode(p.getRegion()+"");
           	region.appendChild(regionV);
           }
           
           vips=documento.createElement("vips");
           carnet.appendChild(vips);
           vipsV=documento.createTextNode(""+getCarnet().getNvips());
           vips.appendChild(vipsV);
           
           estancias=documento.createElement("estancias");
           carnet.appendChild(estancias);
           
           for(Estancia e:estanciasXML) {
           	estancia= documento.createElement("estancia");
           	estancias.appendChild(estancia);
           	
           	idEstancia = documento.createElement("id");
           	estancia.appendChild(idEstancia);
           	idEstanciaV=documento.createTextNode(e.getId().toString());
           	idEstancia.appendChild(idEstanciaV);
           	
           	fecha = documento.createElement("fecha");
           	estancia.appendChild(fecha);
           	fechaV=documento.createTextNode(e.getFecha().toString());
           	fecha.appendChild(fechaV);
           	
           	paradaEs = documento.createElement("parada");
           	estancia.appendChild(paradaEs);
           	paradaEsV=documento.createTextNode(e.getParada().getNombre());
           	paradaEs.appendChild(paradaEsV);
           	
           	if(e.isVip()){
           		vip=documento.createElement("vip");
           		estancia.appendChild(vip);
           	}
           }
           
           
           Source fuente = new DOMSource(documento);
           String ruta="src/main/resources/files/"+getUsuario().getUsuario()+"_peregrino.xml";
           String nombreFichero=(ruta);
           File fichero = new File(nombreFichero);
           Result resultado = new StreamResult(fichero);
           TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
           Transformer transformador = fabricaTransformador.newTransformer();
           transformador.transform(fuente, resultado);
           System.out.println("Exportación finalizada");
           System.out.println("Puede ver su carnet en la carpeta files.");
           System.out.println("");
       } catch (ParserConfigurationException ex) {
           System.out.println("Error: " + ex.getMessage());
       } catch (TransformerConfigurationException ex) {
           System.out.println("Error: " + ex.getMessage());
       } catch (TransformerException ex) {
           System.out.println("Error: " + ex.getMessage());
       }
		
	}
	
	
	//métodos
	
}
