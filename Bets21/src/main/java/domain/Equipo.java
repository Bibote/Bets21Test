package domain;




import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;




@XmlAccessorType(XmlAccessType.FIELD)
@Entity @IdClass(EquipoId.class)
public class Equipo {
	@Id
	private String nombre;
	@Id
	private int temporada; 
	
	private int fundacion;
	
	private String sede;
	private int aforo;
	private int evGanados;
	private int evEmpates;
	private int evPerdidos;
	private String presidente;
	private String entrenador;
	private String web;
	
	//@XmlIDREF
	//private Vector <Event> eventos = new Vector <Event>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector <Jugador> jugadores = new Vector<Jugador>();
	
	
	public Equipo(String nombre, int temporada) {
		
		this.nombre = nombre;
		this.temporada = temporada;
		
		
	}


	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getTemporada() {
		return temporada;
	}



	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}



	public void setJugadores(Vector<Jugador> jugadores) {
		this.jugadores = jugadores;
	}



	public int getEvGanados() {
		return evGanados;
	}


	public void setEvGanados(int evGanados) {
		this.evGanados = evGanados;
	}
	
	public void addEvGanados() {
		this.evGanados ++;
	}
	
	public void subEvGanados() {
		this.evGanados --;
	}
	
//	public void addEvento(Event evento) {
//		
//		eventos.add(evento);
//		
//	}
//	
//	public Vector<Event> getEventos() {
//		
//		return this.eventos;
//	}
	
	public void addJugador(Jugador jugador) {
		jugadores.add(jugador);
	}
	
	public Vector<Jugador> getJugadores(){
		return this.jugadores;
	}
	
	public String toString(){
		return this.nombre;
	}


	public int getFundacion() {
		return fundacion;
	}


	public void setFundacion(int fundacion) {
		this.fundacion = fundacion;
	}


	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}


	public String getPresidente() {
		return presidente;
	}


	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}


	public String getEntrenador() {
		return entrenador;
	}


	public void setEntrenador(String entrenador) {
		this.entrenador = entrenador;
	}


	public String getWeb() {
		return web;
	}


	public void setWeb(String web) {
		this.web = web;
	}



	public int getEvEmpates() {
		return evEmpates;
	}



	public void setEvEmpates(int evEmpates) {
		this.evEmpates = evEmpates;
	}
	
	public void addEvEmpates() {
		this.evEmpates ++;
	}
	public void subEvEmpates() {
		this.evEmpates --;
	}


	public int getEvPerdidos() {
		return evPerdidos;
	}

	public void addEvPerdidos() {
		this.evPerdidos ++;
	}
	
	public void subEvPerdidos() {
		this.evPerdidos --;
	}

	public void setEvPerdidos(int evPerdidos) {
		this.evPerdidos = evPerdidos;
	}



	public int getAforo() {
		return aforo;
	}



	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

}
