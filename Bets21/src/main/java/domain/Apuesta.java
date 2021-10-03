package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apuesta {
	@Id @GeneratedValue
	private Integer numeroPronostico;

	private User user;

	private Pronosticos pronostico;
	private double chutiGoles;

	private Question pregunta;
	private Event evento;
	
	public Apuesta(User user, Pronosticos pronostico, double chutiGoles, Event e, Question pregunta) {
		this.setUser(user);
		this.setPronostico(pronostico);
		this.setChutiGoles(chutiGoles);
		this.setQuestion(pregunta);
		this.setEvento(e);
		
	}

	public Question getQuestion() {
		return pregunta;
	}



	public void setQuestion(Question q) {
		this.pregunta = q;
	}



	public Pronosticos getPronostico() {
		return pronostico;
	}



	public void setPronostico(Pronosticos pronostico) {
		this.pronostico = pronostico;
	}



	public double getChutiGoles() {
		return chutiGoles;
	}



	public void setChutiGoles(double chutiGoles) {
		this.chutiGoles = chutiGoles;
	}


	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Event getEvento() {
		return evento;
	}



	public Integer getNumeroPronostico() {
		return numeroPronostico;
	}

	public void setNumeroPronostico(Integer numeroPronostico) {
		this.numeroPronostico = numeroPronostico;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}


}
