package domain;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Payment {
	@Id @GeneratedValue
	private Integer numeroPayment;
	
	@XmlIDREF
	private User user;
	private Card card;
	private double chutigoles;
	private Date fecha;
	
	
	public Payment(User user, Card card, double chutigoles, Date fecha) {
		this.user = user;
		this.card = card;
		this.chutigoles = chutigoles;
		this.fecha = fecha;
	}


	public Integer getNumeroPayment() {
		return numeroPayment;
	}


	public void setNumeroPayment(Integer numeroPayment) {
		this.numeroPayment = numeroPayment;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Card getCard() {
		return card;
	}


	public void setCard(Card card) {
		this.card = card;
	}


	public double getChutigoles() {
		return chutigoles;
	}


	public void setChutigoles(double chutigoles) {
		this.chutigoles = chutigoles;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
