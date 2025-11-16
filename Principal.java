import javax.swing.JOptionPane; // Para imprimir o pedir datos mediante ventanas.

public class Principal {
    
    public Aeropuerto aeropuerto;

    public static void main(String[] args) {
        Principal p = new Principal();
        p.datosInicializados();
        p.menu();
    }

    public void menu() {
        int opcion;
        do{
            String menu = "Seleccione una opción:\n"
                        + "[1] Ver Aeropuerto\n"
                        + "[2] Obtener estado detallado\n"
                        + "[3] Salir";
            String entrada = JOptionPane.showInputDialog(menu);
            opcion = Integer.parseInt(entrada);

            switch(opcion) {
                case 1:
                    // Aquí iría la lógica para ver el aeropuerto
                    break;
                case 2:
                    // Aquí iría la lógica para obtener el estado detallado
                    
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            }
        }while(opcion != 3);
    }

    public void datosInicializados() {}
}
