package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import exceptions.MaxUsed;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Boleto implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	private String code;
	private Integer max;
	private double valor;
	private Integer usados;
	private Vector<Integer> usuarios;
	
	public Boleto(String code, Integer max, double precio) {
		this.code = code;
		this.max = max;
		this.valor = precio;
		this.usados = 0;
		this.usuarios= new Vector<Integer>();
	}

	public Vector<Integer> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Vector<Integer> usuarios) {
		this.usuarios = usuarios;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public double getPrecio() {
		return valor;
	}

	public void setPrecio(double precio) {
		this.valor = precio;
	}

	public Integer getUsados() {
		return usados;
	}

	public void setUsados(Integer usados) {
		this.usados = usados;
	}
	public void used() throws MaxUsed{
		if (this.usados<this.max) {
			this.usados=this.usados+1;
		}
		else {
			throw new MaxUsed();
		}
	}
	public void addUser(Integer user) {
		this.usuarios.add(user);
	}

}
