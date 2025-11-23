import javax.swing.JOptionPane;
public class Validacion{
    public static int validaInt(String mensaje){
        while (true) {
            try{
                int valor = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
                    return valor;
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Ingresa valor entero valido.");
                }
        }
    }

    public static float validaFloat(String mensaje){
        while (true) {
            try{
                float valor = Float.parseFloat(JOptionPane.showInputDialog(mensaje));
                    return valor;
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Ingresa valor flotante valido.");
                }
        }
    }

    public static long validaLong(String mensaje){
        while (true) {
            try{
                long valor = Long.parseLong(JOptionPane.showInputDialog(mensaje));
                    return valor;
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Ingresa valor long valido.");
                }
        }
    }
}