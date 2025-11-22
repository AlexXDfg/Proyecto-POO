public class Mensaje extends Thread {
    private boolean ejecutandose;
    private Sucursal sucursal;

    public Mensaje(Sucursal sucursal) {
        this.sucursal = sucursal;
        ejecutandose = true;
    }

    public void run() {
        while (ejecutandose) {
            try {
                System.out.println(" Bienvenido a la sucursal " + sucursal.getnoSucursal() + " ubicada en " + sucursal.getDireccion() + ".");
                Thread.sleep(5000);
                System.out.println(" Contamos con " + sucursal.getIndice() + " clientes registrados.");
                Thread.sleep(5000);
                System.out.println(" Contamos con " + sucursal.getIndiceCuentas() + " cuentas registradas.");
                Thread.sleep(5000);
                System.out.println("\u001B[H\u001B[2J");
            } 
            
            catch (InterruptedException e) {
                System.out.println("El hilo de mensajes ha sido interrumpido.");
            }
        }
    }

    public void detener() {
        ejecutandose = false;
    }
    
}
