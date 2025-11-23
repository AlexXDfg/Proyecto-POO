public class Azafata extends Empleado{
    // Atributos
    private int cantProductosVendidos;
    private Vuelo vueloAsignado;

    //Constructores
    public Azafata(int cantProductosVendidos, Vuelo vueloAsignado){
        super();
        this.cantProductosVendidos = cantProductosVendidos;
        this.vueloAsignado = vueloAsignado;
    }
    public Azafata(int cantProductosVendidos, Vuelo vueloAsignado, long idEmpleado, String nombre, int antiguedad, float sueldo){
        super(idEmpleado, nombre, antiguedad, sueldo);
        this.cantProductosVendidos = cantProductosVendidos;
        this.vueloAsignado = vueloAsignado;
    }

    //ToString
    @Override
    public String toString(){
		return super.toString() + " || Cantidad de productos vendidos: " + cantProductosVendidos +
				" || Vuelo asignado: " + vueloAsignado.getNoVuelo();
    }

    //Gets
    public int getCantProductosVendidos(){
        return cantProductosVendidos;
    }
    public Vuelo getVueloAsignado(){
        return vueloAsignado;
    }

    //Setters
	public void setVueloAsignado(Vuelo vueloAsignado){
        this.vueloAsignado = vueloAsignado;
    }
	
	//Calcula el sueldo neto de azafata
	@Override
    public float SueldoNeto() {
        float s = (this.sueldo + (antiguedad * 100) + (cantProductosVendidos * 10)) * (1 + IVA);
        return s;
    }  
}