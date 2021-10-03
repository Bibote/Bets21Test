package domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlIDREF;


public class Jugador {
	
	@Id
	private String nombre;
	
	private int edad;
	private Date fechaNac;
	private double altura;
	
	@XmlIDREF
	private Equipo equipo;
	
	public Jugador(String nombre, Date fechaNac) {
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.setEdad(Calendar.getInstance().get(Calendar.YEAR) - this.fechaNac.getYear());
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	

}
