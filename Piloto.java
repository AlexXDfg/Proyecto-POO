public class Piloto extends Empleado{
	//Atributos
	private int horasVuelo;
	private int licencia; //en regla(1) o no(0)
	private Vuelo vueloAsignado;

	//Constructores
	public Piloto(int horasVuelo, int licencia, Vuelo vueloAsignado){
		super();
		this.horasVuelo = horasVuelo;
		this.licencia = licencia;
		this.vueloAsignado = vueloAsignado;
	}
	public Piloto(int horasVuelo, int licencia, Vuelo vueloAsignado, long idEmpleado, String nombre, int antiguedad, float sueldo){
		super(idEmpleado, nombre, antiguedad, sueldo);
		this.horasVuelo = horasVuelo;
		this.licencia = licencia;
		this.vueloAsignado = vueloAsignado;
	}

	//ToString
	@Override
	public String toString(){
		return super.toString() + " || Horas de vuelo: " + horasVuelo +
				" || Licencia: " + (licencia == 1 ? "En regla" : "No en regla") +
				" || Vuelo asignado: " + vueloAsignado.getNoVuelo();
	}

	//Getters
	public int getHorasVuelo(){
		return horasVuelo;
	}
	public int getLicencia(){
		return licencia;
	}
	public Vuelo getVueloAsignado(){
		return vueloAsignado;
	}
	
	//Setters
	public void setVueloAsignado(Vuelo vueloAsignado){
        this.vueloAsignado = vueloAsignado;
    }

	//Calcula el sueldo neto del piloto
	@Override
	public float SueldoNeto(){
		float s = (this.sueldo + (antiguedad * 100) + (horasVuelo * 50)) * (1 + IVA);
		return s;
	}

	// ---------------------------------------------------------
    // CLASE ANIDADA: COPILOTO
    // ---------------------------------------------------------
	public class Copiloto implements Serializable{
        private String nombre;
        private int licenciaCop;

        public Copiloto(String nombre, int licenciaCop) {
            this.nombre = nombre;
            this.licenciaCop = licenciaCop;
        }

        @Override
        public String toString() {
            long vueloId = getVueloAsignado().getNoVuelo();
            String estadoLic = (licenciaCop == 1) ? "En regla" : "Vencida";
            
            return "Copiloto: " + nombre + " (Licencia: " + estadoLic + ") apoyando en vuelo #" + vueloId;
        }
        
        public String getNombre(){
			return nombre;
		}
        public int getLicenciaCop(){
			return licenciaCop;
		}
    }
}
