package model;

public class Jugador {
	private String name;
	private int pantalla;
	private int puntuacion;
	private String status;
	
	public Jugador (String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public int getPantalla() {
		return pantalla;
	}

	public void setPantalla(int pantalla) {
		this.pantalla = pantalla;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Jugador [name=" + name + ", pantalla=" + pantalla
				+ ", puntuacion=" + puntuacion + ", status=" + status + "]";
	}
	
}
