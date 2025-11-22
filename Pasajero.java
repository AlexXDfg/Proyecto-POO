import java.io.Serializable;
public class Pasajero implements Serializable{
    // Atributos
    private String nombre;
    private long idPasajero;
    private String documento;
    private String telefono;

    //Constructor
    public Pasajero(String nombre, long idPasajero, String documento, String telefono){
        this.nombre = nombre;
        this.idPasajero = idPasajero;
        this.documento = documento;
        this.telefono = telefono;
    }
	
	//toString del pasajero
    @Override
    public String toString(){
        return "Pasajero: " + nombre +
               " || ID: " + idPasajero +
               " || Documento: " + documento +
			   " || Telefono: " + telefono;
    }

    //Getters
    public String getNombre(){
        return nombre;
    }
    public long getIdPasajero(){
        return idPasajero;
    }
    public String getDocumento(){
        return documento;
    }
    public String getTelefono(){
        return telefono;
    }
}


