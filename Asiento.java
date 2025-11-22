import java.io.Serializable;
public class Asiento implements Verificable, Serializable{
	// Atributos
	private long noAsiento;
	private int disponibilidad; // 0(no disponible) o 1(disponible)
	private String tipo;
	private Vuelo vueloAsignado;
	private Boleto boletoAsignado;

	//Constructor
	public Asiento(long noAsiento, String tipo, Vuelo vueloAsignado){
		this.noAsiento = noAsiento;
		this.disponibilidad = 1;
		this.tipo = tipo;
		this.vueloAsignado = vueloAsignado;
		this.boletoAsignado = null;
	}
	
	//toString del asiento
	@Override
	public String toString(){
		return "Asiento #" + noAsiento +
			   " || Vuelo #" + vueloAsignado.getNoVuelo() +
			   " || Tipo: " + tipo +
			   " || Estado: " + ((disponibilidad == 1) ? "Disponible" : "Ocupado");
	}

	//Getters
	public long getNoAsiento(){
		return noAsiento;
	}
	public int getDisponibilidad(){
		return disponibilidad;
	}
	public String getTipo(){
		return tipo;
	}
	public Vuelo getVueloAsignado(){
		return vueloAsignado;
	}
	public Boleto getBoletoAsignado(){
		return boletoAsignado;
	}
	
	//Asigna un boleto al asiento y lo actualiza como no disponible
	public void asignarBoleto(Boleto boleto) {
		boletoAsignado = boleto;
		disponibilidad = 0; // 0 = no disponible
	}

	//Metodos de la interface 'Verificable'
    @Override
    public boolean estaDisponible(){
        return this.disponibilidad == 1; // Devuelve true si disponibilidad es 1
    }
    @Override
    public String getEstadoDetallado(){
		return (this.disponibilidad == 1) ? "Disponible" : "Ocupado";
    }
}


