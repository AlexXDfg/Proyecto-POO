public class Piloto extends Empleado{
	//Atributos
	private int horasVuelo;
	private int licencia; //en regla(1) o no(0)
	private Vuelo vueloAsignado;
	private Copiloto copiloto;

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
	public void setCopiloto(Copiloto copiloto){
		this.copiloto = copiloto;
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
	class Copiloto extends Empleado{
        private int licenciaCop;

        public Copiloto(long id, String nombre, int antiguedad, float sueldo, int licenciaCop) {
            super(id, nombre, antiguedad, sueldo);
            this.licenciaCop = licenciaCop;
        }

        @Override
        public String toString() {
            long vueloId = getVueloAsignado().getNoVuelo();
            return "ID del empleado: " + idEmpleado + " || Copiloto: " + nombre + " || Antiguedad: " + antiguedad + " || Sueldo: " + sueldo + " || Licencia: " + ((licenciaCop == 1) ? "En regla" : "Vencida") + " || Apoyando en vuelo: " + vueloId;
        }

		@Override
        public float SueldoNeto() {
            return (this.sueldo + (antiguedad * 50) + (horasVuelo * 25)) * (1 + IVA);
        }

		//Getter
		public int getLicencia(){
			return licenciaCop;
		}
    }
}

