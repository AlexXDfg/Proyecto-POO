import java.io.Serializable;
public abstract class Empleado implements Serializable{
	//Atributos
	protected long idEmpleado;
	protected String nombre;
	protected int antiguedad;//Cantidad en años
	protected float sueldo;
	protected static float IVA = 0.16f;

	//Constructores
	public Empleado(){}
	public Empleado(long idEmpleado, String nombre, int antiguedad, float sueldo) {
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.antiguedad = antiguedad;
		this.sueldo = sueldo;
	}

	//ToString
	@Override
	public String toString(){
		return	"ID del empleado: " + idEmpleado +
				" || Nombre: " + nombre +
				" || Antiguedad: " + antiguedad + " años" +
				" || Sueldo: " + sueldo;
	}
	
	//Getters
	public long getIdEmpleado(){
		return idEmpleado;
	}
	public String getNombre(){
		return nombre;
	}
	public int getAntiguedad(){
		return antiguedad;
	}
	public float getSueldo(){
		return sueldo;
	}
	
	//Metodo abstracto (para heredar) que calcula el sueldo neto de cada empleado
	public abstract float SueldoNeto();

}

