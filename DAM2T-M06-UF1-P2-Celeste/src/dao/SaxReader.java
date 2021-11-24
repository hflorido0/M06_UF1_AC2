package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Jugador;

public class SaxReader extends DefaultHandler {
	private ArrayList<Jugador> jugadores;
	private Jugador jugador;
	private String value;
	private SAXParser parser;
	private File file;
	
	public ArrayList<Jugador> getJugadores() {
		return this.jugadores;
	}
	
	public SaxReader(String url) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			parser = factory.newSAXParser();
			file = new File ("files/entrada.xml");
		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		}
	}
	
	public void parse() {
		try {
			parser.parse(file, this);
		} catch (SAXException e) {
			System.out.println("ERROR parsing file");
		} catch (IOException e) {
			System.out.println("ERROR reading from file");
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		this.jugadores = new ArrayList<>();
	}

	@Override
	public void endDocument() throws SAXException {
		printDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName) {
			case "partida":
				this.jugador = new Jugador(attributes.getValue("jugador"));
				break;
			case "pantalla":
				this.jugador.setStatus(attributes.getValue("estado"));
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//we add the product into the arrayList
		if (qName.equals("partida")) this.jugadores.add(jugador);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		value = new String(ch,start,length);
		
		if (value.matches("[0-9]*")) this.jugador.setPuntuacion(Integer.valueOf(value));
		else if(value.matches("#[0-9]*")) this.jugador.setPantalla(Integer.valueOf(value.replace("#", "")));
	}

	private void printDocument() {
		for (Jugador j : jugadores) {
			System.out.println(j.toString());
		}
	}

}
