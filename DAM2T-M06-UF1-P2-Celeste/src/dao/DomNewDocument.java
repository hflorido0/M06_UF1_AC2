package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Jugador;

public class DomNewDocument {
	private Document document;
	
	public DomNewDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating doucment");
		}
	}
	

	public void generateDocument(ArrayList<Jugador> jugadores, ArrayList<String[][]> niveles) {
		Element pantallas = document.createElement("pantallas");
		document.appendChild(pantallas);
		
		for (Jugador j : jugadores) {

			int pantallaCompleta = -1;
			if (!j.getStatus().equals("completa")) pantallaCompleta--;
			
			Element pantalla = document.createElement("pantalla");
			pantalla.setAttribute("jugador", j.getName());
			pantalla.setAttribute("nivel", String.valueOf(j.getPantalla()+pantallaCompleta+1));
			pantallas.appendChild(pantalla);
			
			String[][] nivelActual = niveles.get(j.getPantalla()+pantallaCompleta);
			
			if (pantallaCompleta != 0) {
				for (int i = 0; i < nivelActual.length; i++) {
					for (int k = 0; k < nivelActual[i].length; k++) {
						Element pixel = document.createElement("pixel");
						pixel.setAttribute("fil", i+"");
						pixel.setAttribute("col", k+"");
						pixel.setTextContent(nivelActual[i][k]);
						pantalla.appendChild(pixel);
					}
				}
			}
			
			Element puntuacion = document.createElement("puntuacion");
			puntuacion.setTextContent(String.valueOf(j.getPuntuacion()));
			pantalla.appendChild(puntuacion);
		}
	}
	
	public void generateXml(String url) {
		try {			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			Source source = new DOMSource(document);
			File file = new File(url);
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);
			
			transformer.transform(source, result);
		} catch (IOException e) {
			System.out.println("Error when creating writter file");
		} catch (TransformerException e) {
			System.out.println("Error transforming the document");
		}
	}
}
