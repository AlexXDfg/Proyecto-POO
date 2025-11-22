import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class Persistencia{
    //lee objetos del archivo
    public static Sucursal cargaArchivoPesistencia(){
        FileInputStream fisPer = null;
        try {
            fisPer = new FileInputStream("Banco.ser");
            
            ObjectInputStream oisPer = new ObjectInputStream(fisPer);
            
            //lee objeto
            Sucursal objeto = (Sucursal) oisPer.readObject();

            return objeto;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println ("fin de lectura");
        }
        finally{
            try {
                fisPer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //guarda todos los objetos por modificaciones
    public static void guardaPersistencia(Sucursal objeto) {
        
        FileOutputStream archivo;
        File f = new File("Banco.ser");
        
        try {
            //segundo parametro true agrega, false sobreescribe
            archivo = new FileOutputStream(f, false);
            
            ObjectOutputStream oos= new ObjectOutputStream(archivo);
            
            oos.writeObject(objeto);
            
            oos.close();
            
            archivo.close();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar objeto");
        }
    }
}