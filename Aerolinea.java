import java.io.Serializable;
public class Aerolinea implements Serializable{
	//Atributos
	private String nombre;
	private Vuelo vuelos[] = new Vuelo[10];
	private int indVuelo;

	//Constructoresnnnnnnn
	public Aerolinea(){}
	public Aerolinea(String nombre){
		this.nombre = nombre;
		indVuelo = 0;
	}
	
	//toString de la aerolinea
	@Override
	public String toString(){
        return "Aerolinea: " + nombre;
    }

	//Getters
	public String getNombre(){
		return nombre;
	}
	public Vuelo[] getVuelos(){
		return vuelos;
	}
	public int getIndVuelo(){
		return indVuelo;
	}
	
	//Metodos para agregar a la aerolinea
	public int addVuelo(Vuelo vuelo){
		if(indVuelo < vuelos.length){
			vuelos[indVuelo++] = vuelo;
			return 1;
		}else
			return 0;
	}
	
	public Vuelo[] getVuelosDisponibles(){
		int cantDisponibles = 0;
		for(int i = 0; i < indVuelo; i++){
			if(vuelos[i].getEstado() == 1)
				cantDisponibles++;
		}

		Vuelo[] vuelosDisp = new Vuelo[cantDisponibles];
		
		int j = 0;
		for(int i = 0; i < indVuelo; i++){
			if (vuelos[i].getEstado() == 1)
				vuelosDisp[j++] = vuelos[i];
		}
		
		return vuelosDisp;
	}
}



