public class AgenteMostrador extends Empleado {
	//Atributos
	private Boleto boletos[] = new Boleto[600];//Cada vuelo tiene 20 asientos disponibles
	private int indBoletos; //cantidad de boletos vendidos por el agente

	//Constructores
	public AgenteMostrador(){
		super();
		this.indBoletos = 0;
	}
	public AgenteMostrador(long idEmpleado, String nombre, int antiguedad, float sueldo){
		super(idEmpleado, nombre, antiguedad, sueldo);
		this.indBoletos = 0;
	}

	//ToString
	@Override
	public String toString(){
		return super.toString() + " || Cantidad de boletos vendidos: " + indBoletos;
	}

	//Getters
	public int getIndBoletos(){
		return indBoletos;
	}
	public Boleto[] getBoletosVendidos(){
		return boletos;
	}
	
	//Registra la venta de un Boleto
	public int registrarVenta(Boleto boletoVendido) {
        if(indBoletos < boletos.length){
            boletos[indBoletos++] = boletoVendido;
            return 1; //Venta registrada
        }
        return 0; //Ya no hay boletos disponibles
    }
	
	//Calcula el sueldo neto del agente de mostrador
	@Override
	public float SueldoNeto() { 
		float s = (this.sueldo + (antiguedad * 100) + (indBoletos * 10)) * (1 + IVA);
        return s;
	}
}