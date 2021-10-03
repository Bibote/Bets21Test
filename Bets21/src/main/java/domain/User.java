package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
/**
 * Clase que crea un usuario
 *
 */
public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id 
	private Integer DNI;
	private boolean ban;
	private String message = "";
	private Integer numMovil;
	private String contrasena;
	private String Nombre, Apellido1, Apellido2, email;
	private Date fechaNac;
	private Double chutiGoles;
	private Boolean privilegiado; // true = administrador, false = cliente
	
	//@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Apuesta> apuestas =new Vector<Apuesta>();
	private Vector<Card> cards =  new Vector<Card>();
	private Vector<Payment> payments= new Vector<Payment>();
	
	

	/**
	 * Constructora de la clase User
	 * @param numMovil Integer que representa el numero de un telefono movil
	 * @param dNI Integer que representa un Dni 
	 * @param contrase�a String que representa una contrase�a
	 * @param nombre String que representa un nombre
	 * @param apellido1 String que representa un apellido
	 * @param apellido2 String que representa un apellido
	 * @param email String que representa una direccion email
	 * @param fechaNac Date que representa la fecha de nacimiento
	 */
	public User(Integer numMovil, Integer dNI, String contrasena, String nombre, String apellido1, String apellido2, String email,
			Date fechaNac) {
		super();
		this.numMovil = numMovil;
		this.contrasena = contrasena;
		DNI = dNI;
		Nombre = nombre;
		Apellido1 = apellido1;
		Apellido2 = apellido2;
		this.email = email;
		this.fechaNac = fechaNac;
		this.chutiGoles = 0.0;
		this.privilegiado = false;
		this.ban = false;
		
	}
	/**
     * Constructora de la clase User
     * @param DNI Integer que representa un Dni
     * @param contrase�a String que representa una contrase�a
     * @param nombre String que representa un nombre
     * @param apellido1 String que representa un apellido
     * @param apellido2 String que representa un apellido
     * @param email String que representa un email
     * @param fechaNac Date que representa una fecha de nacimiento
     */
	public User(Integer DNI, String contrasena,String nombre, String apellido1, String apellido2, String email,
			Date fechaNac) {
		super();
		this.numMovil = 0;
		this.DNI = DNI;
		this.contrasena = contrasena;
		Nombre = nombre;
		Apellido1 = apellido1;
		Apellido2 = apellido2;
		this.email = email;
		this.fechaNac = fechaNac;
		this.chutiGoles = 1000000.0;
		this.privilegiado = true;
		this.ban = false;

	}
	
	public int getNumMovil() {
		return numMovil;
	}
	
	public String getcontrasena() {
		return contrasena;
	}

	public void setcontrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getDNI() {
		return DNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getChutiGoles() {
		return chutiGoles;
	}

	public void setChutiGoles(Double chutiGoles) {
		this.chutiGoles = chutiGoles;
	}
	public void addChutiGoles(Double chutiGoles) {
		this.chutiGoles+= chutiGoles;
	}
	

	public Date getFechaNac() {
		return fechaNac;
	}

	public Boolean getPrivilegiado() {
		return privilegiado;
	}
	
	public String getNombre() {
		return Nombre;
	}

	public String getApellido1() {
		return Apellido1;
	}

	public String getApellido2() {
		return Apellido2;
	
	}
	public String toString() {
		
		return this.Nombre+", "+this.Apellido1+", "+this.Apellido2+"\nEmail: "+this.email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isBan() {
		return ban;
	}
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	public Vector<Apuesta> getApuestas() {
		return apuestas;
	}
	public void setApuestas(Vector<Apuesta> apuestas) {
		this.apuestas = apuestas;
	}
	
	public void eliminarApuesta(Apuesta ap) {
		for (int i = 0; i<apuestas.size();i++) {
			if (apuestas.get(i).getNumeroPronostico() == ap.getNumeroPronostico()) {
				apuestas.remove(i);
				return;
			}
		}
	}
	
	public void addApuesta(Apuesta apuesta) {
		this.apuestas.add(apuesta);
	}
	
	
	public Vector<Card> getCards() {
		return cards;
	}
	public void setCards(Vector<Card> cards) {
		this.cards = cards;
	}

	public void addCards(Card tarjeta) {
		this.cards.add(tarjeta);
	}
	
	public Vector<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Vector<Payment> payments) {
		this.payments = payments;
	}
	
	public void addPayments(Payment pago) {
		this.payments.add(pago);
	}
}