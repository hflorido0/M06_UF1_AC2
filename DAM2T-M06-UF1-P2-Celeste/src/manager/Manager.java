package manager;
import java.util.ArrayList;

import dao.DomNewDocument;
import dao.Reader;
import dao.SaxReader;
import model.Jugador;

public class Manager {
	private static Manager manager;
	private ArrayList<String[][]> niveles;
	private Reader reader;

	private Manager () {
		this.reader = new Reader("files/pantallas.txt");
		this.niveles = new ArrayList<>();
	}
	
	public static Manager getInstance() {
		if (manager == null) {
			manager = new Manager();
		}
		return manager;
	}

	public void init() {
		readFilePartidas();
		generageNewXml(readXmlFile());
	}
	
	private void generageNewXml(ArrayList<Jugador> jugadores) {
		DomNewDocument domDoc = new DomNewDocument();
		domDoc.generateDocument(jugadores, niveles);
		domDoc.generateXml("files/salida.xml");
	}

	private ArrayList<Jugador> readXmlFile() {
		SaxReader saxReader = new SaxReader("files/entrada.xml");
		saxReader.parse();
		return saxReader.getJugadores();
	}

	private void readFilePartidas() {
		String line;
		int fila = 0;
		String[][] nivel = new String[5][10];
		while ((line = reader.read()) != null) {
			if (!line.contains("#")) {
				nivel[fila] = line.split(" ");
				fila++;
			} else {
				if (fila > 0) this.niveles.add(nivel);
				nivel = new String[5][10];
				fila = 0;
			}
		}
	}
	
}
