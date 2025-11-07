public class Boleto{
    // Atributos
    private float precio;
    private long noBoleto;
    private Asiento asientoAsignado;
    private String clase;	// Primera clase o economica

    //Constructor
    public Boleto(float precio, long noBoleto, Asiento asientoAsignado, String clase){
        this.precio = precio;
        this.noBoleto = noBoleto;
        this.asientoAsignado = asientoAsignado;
        this.clase = clase;
    }
	
	//toString del Boleto
    @Override
    public String toString(){
        return "Boleto #" + noBoleto +
               " || Clase: " + clase +
               " || Asiento: " + asientoAsignado.getNoAsiento() +
               " || Precio: $" + precio;
    }

    //Getters
    public float getPrecio(){
        return precio;
    }
    public long getNoBoleto(){
        return noBoleto;
    }
    public Asiento getAsientoAsignado(){
        return asientoAsignado;
    }
    public String getClase(){
        return clase;
    }
}
