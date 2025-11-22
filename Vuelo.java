public class Vuelo implements Verificable, Serializable{
// Atributos
	private long noVuelo;
	private String origen;
	private String destino;
	private String horaSalida;
	private String horaLlegada;
	private String duracion;
	private int estado; // 0(no disponible) o 1(disponible)
	private Asiento asientos[] = new Asiento[20];

	//Constructor	
	public Vuelo(long noVuelo, String origen, String destino, String horaSalida, String horaLlegada, String duracion){
		this.noVuelo = noVuelo;
		this.origen = origen;
		this.destino = destino;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.duracion = duracion;
		this.estado = 1; // Está disponible al crearse
		this.crearAsientos();
	}
	
	//toString del vuelo
	@Override
    public String toString(){
        return "Vuelo #" + noVuelo +
               " || " + origen + " -> " + destino +
               " || Salida: " + horaSalida +
               " || Estado: " + ((estado == 1) ? "Disponible" : "No Disponible");
    }

	//Getters
	public long getNoVuelo(){
		return noVuelo;
	}
	public String getOrigen(){
		return origen;
	}
	public String getDestino(){
		return destino;
	}
	public String getHoraSalida(){
		return horaSalida;
	}
	public String getHoraLlegada(){
		return horaLlegada;
	}
	public String getDuracion(){
		return duracion;
	}
	public int getEstado(){
		return estado;
	}
	public Asiento[] getAsientos(){
		return asientos;
	}

	//Setter
	public void setEstado(int estado){
		this.estado = estado;
	}

	//Crea los asientos
	private void crearAsientos(){
		for(int i = 0; i < asientos.length; i++){
			if(i < 5)
				asientos[i] = new Asiento(i + 1, "Primera Clase", this);
			else
				asientos[i] = new Asiento(i + 1, "Economica", this);
		}
	}

	//Busca un asiento por el No. de asiento y retorna el Asiento
	public Asiento buscarAsiento(long noAsiento) {
        if (noAsiento > 0 && noAsiento <= asientos.length) {
            return asientos[(int) noAsiento - 1];
        }
        return null; // No se encontró el asiento
    }

	//Metodos de la interface 'Verificable'
	@Override
    public boolean estaDisponible() {
		return this.estado == 1; // Devuelve true si estado es 1
    }
    @Override
    public String getEstadoDetallado() {
        return (this.estado == 1) ? "Disponible" : "No Disponible";
    }
}


