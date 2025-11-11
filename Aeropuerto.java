public class Aeropuerto{
	//Atributos
	private String nombre;
	private String direccion;
	
	//Aerolineas
	private Aerolinea aerolineas[] = new Aerolinea[3];
	private int indAerolineas;
	
	//Vuelos
	private Vuelo vuelos[] = new Vuelo[30];//Cada aerolinea tiene 10 vuelos, en total hay 30 vuelos
	private int indVuelos;
	
	//Empleados
	private Empleado empleados[] = new Empleado[120];//30 Pilotos, 30 Agentes de Mostrador, 60 azafatas
	private int indEmpleados;

	//Constructores
	public Aeropuerto(){}
	public Aeropuerto(String nombre, String direccion){
		this.nombre = nombre;
		this.direccion = direccion;
		indAerolineas = 0;
		indVuelos = 0;
		indEmpleados = 0;
	}

	//toString del Aeropuerto
	@Override
	public String toString(){
        return "Aeropuerto: " + nombre +
				"\nUbicado en: "+ direccion;
    }

	//Getters
	public String getNombre(){
		return nombre;
	}
	public String getDireccion(){
		return direccion;
	}
	public Aerolinea[] getAerolineas(){
		return aerolineas;
	}
	public int getIndAerolineas(){
		return indAerolineas;
	}
	public Vuelo[] getVuelos(){
		return vuelos;
	}
	public int getIndVuelos(){
		return indVuelos;
	}
	public Empleado[] getEmpleados(){
		return empleados;
	}
	public int getIndEmpleados(){
		return indEmpleados;
	}

	//Metodos de agregar al aeropuerto
	public int addAerolinea(String nombre){
		if(indAerolineas < aerolineas.length){
			aerolineas[indAerolineas++] = new Aerolinea(nombre);
			return 1;
		}
		return 0;	//No se pueden agregar mas aerolineas
	}
	public int addVuelo(Vuelo vuelo, int indice){
		if(indVuelos < vuelos.length){
			if(aerolineas[indice].addVuelo(vuelo) == 1){
				vuelos[indVuelos++] = vuelo;
				return 1;
			}
		}
		return 0;	//No se pueden agregar mas vuelos
	}
	public int addEmpleado(Empleado emp){
		if(indEmpleados < empleados.length){
			empleados[indEmpleados++] = emp;
			return 1;
		}
		return 0;	//No se pueden agregar mas empleados
	}
	
	//Metodos para buscar en el aeropuerto
	public Vuelo buscarVuelo(long noVuelo){
		for(int i = 0; i < indVuelos; i++){
			if(vuelos[i].getNoVuelo() == noVuelo){
				return vuelos[i];
			}
		}
		return null;	//No se encontro el vuelo
	}
	public Empleado buscarEmpleado(long idEmpleado) {
        for (int i = 0; i < indEmpleados; i++) {
            if (empleados[i].getIdEmpleado() == idEmpleado) {
                return empleados[i];
            }
        }
        return null;	//No se encontro el empleado
    }
}

