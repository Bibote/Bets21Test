package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
/**
 * Clase que genera objetos de tipo Pronosticos
 *
 */
public class Pronosticos {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer prognosticNumber;
	private String pronostico;
	private float porcentaje;
	private Equipo equipo = null;
	@XmlIDREF
	private Question question;
	/**
	 * Contructora que inicializa un objeto de tipo Pronosticos
	 * @param pronostico
	 * @param porcentaje
	 * @param q Pregunta que hace referencia a este pronostico
	 */
	public Pronosticos(String pronostico, float porcentaje, Question q) {
		this.pronostico = pronostico;
		this.porcentaje = porcentaje;
		this.question = q;
		
	}
	
	public Pronosticos(Equipo equipo, float porcentaje, Question q) {
		this.equipo = equipo;
		this.porcentaje = porcentaje;
		this.question = q;
		this.pronostico = equipo.getNombre();
	}
	
	public Integer getPrognosticNumber() {
		return prognosticNumber;
	}
	public void setPrognosticNumber(Integer prognosticNumber) {
		this.prognosticNumber = prognosticNumber;
	}
	public String getPronostico() {
		return pronostico;
	}
	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public String toString() {
		if (this.equipo == null) {
		return (this.pronostico+". "+this.porcentaje);
		}else {
			return (this.equipo.toString()+". "+this.porcentaje);
		}
	}

	public Equipo getEq() {
		return equipo;
	}

	public void setEq(Equipo eq) {
		this.equipo = eq;
	}
	
	

}
