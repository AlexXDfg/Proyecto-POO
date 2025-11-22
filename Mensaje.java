public class Mensaje extends Thread {
    private boolean ejecutandose;
    private Aeropuerto aeropuerto;

    public Mensaje(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        ejecutandose = true;
    }

    public void run() {
        while (ejecutandose) {
            try {
                System.out.println(" Bienvenido a nuestro sistema de Aeropuertos " + aeropuerto.getNombre() + " ubicada en " + aeropuerto.getDireccion() + ".");
                Thread.sleep(5000);
                System.out.println(" Contamos con " + aeropuerto.getIndAerolineas() + " aerolineas asociadas.");
                Thread.sleep(5000);
                System.out.println(" Y manejamos hasta " + aeropuerto.getIndVuelos() + " vuelos.");
                Thread.sleep(5000);
                System.out.println(" Actualmente contamos con " + aeropuerto.getIndEmpleados() + " empleados trabajando en nuestro aeropuerto.");
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

