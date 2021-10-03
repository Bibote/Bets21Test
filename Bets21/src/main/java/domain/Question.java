package domain;

import java.io.*;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
/**
 * Clase que crea preguntas
 *
 */
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	private String result;  
	@XmlIDREF
	private Event event;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Pronosticos> pronosticos = new Vector<Pronosticos>();
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Apuesta> apuestas = new Vector<Apuesta>();
	
	private boolean estado;
	private boolean equipo;//si la respuesta se trata de un equipo
	private Pronosticos PronosticoFinal;

	public Question(){
		super();
	}
	/**
     * Constructora de la clase Question que requiere de argumento para la clave privada
     * @param queryNumber Integer que representra el numero de la pregunta en la base de datos
     * @param query String que representa la pregunta creada
     * @param betMinimum Float que representa la apuesta minima realizable
     * @param event Event que representa el evento donde se crea la pregunta
     */
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.setEstado(true);
		
	}
	/**
     * Constructora de la clase question, la clave privada es implicita
     * @param query String que representa la pregunta creada
     * @param betMinimum Float que representa la apusta minima realizable
     * @param event Event que representa el evento donde se crea la pregunta 
     */
	public Question(String query, float betMinimum,  Event event, Boolean equipo) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;
		this.setEstado(true);
		this.setEquipo(equipo);

	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}




	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}

	/**
     * Metodo que anade un pronostico 
     * @param pronos String que representa un pronostico
     * @param porcentaje Float que representa el porcentaje 
     * @return un pronostico
     */
	public Pronosticos addPronostico(String pronos, float porcentaje, Equipo eq)  {
		
		if (eq == null) {
			Pronosticos p=new Pronosticos(pronos,porcentaje, this);
			pronosticos.add(p);
			return p;
		}
		else {
			Pronosticos p=new Pronosticos(eq,porcentaje, this);
			pronosticos.add(p);
			return p;
		}
	}
	
	public Vector<Pronosticos> getPronosticos() {
		return pronosticos;
	}
	
	public Apuesta addApuesta(Apuesta a)  {
        
        apuestas.add(a);
        return a;
	}
	
	public Vector<Apuesta>getApuestas(){
		return apuestas;
	}
	
	public void eliminarApuesta(Apuesta ap) {
		for (int i = 0; i<apuestas.size();i++) {
			if (apuestas.get(i).getNumeroPronostico() == ap.getNumeroPronostico()) {
				apuestas.remove(i);
				return;
			}
		}
	}
	
	/**
     * Metodo que comprueba si un pronostico ya existe
     * @param pronostico String que representa un pronostico
     * @return true si lo es, false si no
     */
	public boolean doesPrognosticExists(String pronostico, Equipo eq) {
		
		if (eq == null) {
		for (Pronosticos p:this.getPronosticos()) {
			if (p.getPronostico().compareTo(pronostico)==0) {
				return true;
			}
		}
		return false;
		}else {
			for (Pronosticos p:this.getPronosticos()) {
				if (p.getEq().toString().equals(eq.toString())) {
					return true;
				}
			}
			return false;
			
		}
	}

	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Pronosticos getPronosticoFinal() {
		return PronosticoFinal;
	}
	public void setPronosticoFinal(Pronosticos pronosticoFinal) {
		PronosticoFinal = pronosticoFinal;
	}
	public boolean isEquipo() {
		return equipo;
	}
	public void setEquipo(boolean equipo) {
		this.equipo = equipo;
	}


	
}