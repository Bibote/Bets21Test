package domain;

public class EquipoId {
	
	String nombre;
    int  temporada; 
    
    public EquipoId(String nombre, int temporada) {
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
    
    
	
}