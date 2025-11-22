import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        Principal p = new Principal();
        Aeropuerto aeropuerto = p.datosInicializados();
        p.menu(aeropuerto);
    }

    public void menu(Aeropuerto aeropuerto) {
        
        // Cargar persistencia si existe.
        Aeropuerto persistencia = Persistencia.cargaArchivoPesistencia();
        if (persistencia != null) {
            aeropuerto = persistencia;
        }
        
        // Hilo de mensajes
        Mensaje mensaje = new Mensaje(aeropuerto);
        Thread mensajero = new Thread(mensaje);
        mensajero.start(); 

        int opcion;

        do {
            String entrada = JOptionPane.showInputDialog(
                "--- AEROPUERTO: " + aeropuerto.getNombre() + " ---\n" +
                "1. Ver Información del Aeropuerto\n" +
                "2. Ver Vuelos Disponibles\n" +
                "3. Ver Nómina de Empleados\n" +
                "4. Vender Boleto\n" +
                "5. Verificar Estado (Vuelo/Asiento)\n" +
                "6. Guardar y Salir\n" +
                "--------------------------------\n" +
                "Seleccione una opción:"
            );
            
            switch(opcion) {
                case 1: // Ver Aeropuerto
                    JOptionPane.showMessageDialog(null, aeropuerto.toString());
                    break;

                case 2: // Ver Vuelos
                    if (verificarVuelos(aeropuerto) == 0) 
                        break;
                    mostrarVuelos(aeropuerto);
                    break;

                case 3: // Ver Nómina
                    mostrarNomina(aeropuerto);
                    break;

                case 4: // Vender Boleto
                    // Verificaciones previas
                    if (verificarVuelos(aeropuerto) == 0) 
                        break;
                    
                    // 1. Mostrar y Seleccionar Vuelo
                    mostrarVuelos(aeropuerto);
                    Vuelo vueloSel = seleccionarVuelo(aeropuerto);
                    if (vueloSel == null) 
                        break; // Si no encontró vuelo, salir del case

                    // 2. Mostrar y Seleccionar Asiento
                    mostrarAsientos(vueloSel);
                    Asiento asientoSel = seleccionarAsiento(vueloSel);
                    if (asientoSel == null) 
                        break;

                    // 3. Crear el Pasajero (Método similar a crearCliente)
                    // "El objeto que envien lo creen en el mismo metodo antes de enviarlo"
                    Pasajero pasajero = crearPasajero();

                    // 4. Datos adicionales para la venta
                    long idAgente = Long.parseLong(JOptionPane.showInputDialog("ID del Agente (Ej. 1000):"));
                    float precio = Float.parseFloat(JOptionPane.showInputDialog("Precio del boleto:"));
                    long noBoleto = System.currentTimeMillis(); // ID único simple

                    // 5. Enviar objetos al método del aeropuerto
                    Boleto boletoGenerado = aeropuerto.gestionarVentaBoleto(
                        idAgente, 
                        vueloSel.getNoVuelo(), 
                        asientoSel.getNoAsiento(), 
                        pasajero, 
                        noBoleto, 
                        precio
                    );

                    if (boletoGenerado != null) {
                        JOptionPane.showMessageDialog(null, "¡Venta Exitosa!\n" + boletoGenerado.toString());
                    } 
                    
                    else {
                        JOptionPane.showMessageDialog(null, "Error: Verifique ID Agente o disponibilidad.");
                    }
                    break;

                case 5: // Verificar Estado (Interface)
                    String tipo = JOptionPane.showInputDialog("Escriba 'vuelo' o 'asiento':");
                    
                    if (tipo != null && tipo.equalsIgnoreCase("vuelo")) {
                        Vuelo v = seleccionarVuelo(aeropuerto);
                        if (v != null) 
                            JOptionPane.showMessageDialog(null, "Estado: " + v.getEstadoDetallado());
                            
                    } else if (tipo != null && tipo.equalsIgnoreCase("asiento")) {
                        long idVuelo = Long.parseLong(JOptionPane.showInputDialog("ID del Vuelo del asiento:"));
                        Vuelo v = aeropuerto.buscarVuelo(idVuelo);
                        if (v != null) {
                            Asiento a = seleccionarAsiento(v);
                            if (a != null)
                                JOptionPane.showMessageDialog(null, "Estado: " + a.getEstadoDetallado());
                        } 
                        
                        else {
                            JOptionPane.showMessageDialog(null, "Vuelo no encontrado.");
                        }
                    }
                    break;

                case 6: // Guardar y Salir
                    mensaje.detener();
                    Persistencia.guardaPersistencia(aeropuerto);
                    JOptionPane.showMessageDialog(null, "Guardando y saliendo...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }

        } while(opcion != 6);
    }

    public Pasajero crearPasajero() {
        String nombre = JOptionPane.showInputDialog("Nombre del Pasajero:");
        long id = Long.parseLong(JOptionPane.showInputDialog("ID del Pasajero:"));
        String doc = JOptionPane.showInputDialog("Documento:");
        String tel = JOptionPane.showInputDialog("Teléfono:");

        Pasajero nuevo = new Pasajero(nombre, id, doc, tel);
        return nuevo;
    }

    public Vuelo seleccionarVuelo(Aeropuerto aeropuerto) {
        long noVuelo = Long.parseLong(JOptionPane.showInputDialog("Ingrese el Número (#) de Vuelo:"));
        Vuelo v = aeropuerto.buscarVuelo(noVuelo);
        if (v == null) {
            JOptionPane.showMessageDialog(null, "Vuelo no encontrado.");
        }
        return v;
    }

    public Asiento seleccionarAsiento(Vuelo vuelo) {
        long noAsiento = Long.parseLong(JOptionPane.showInputDialog("Ingrese el Número de Asiento:"));
        Asiento a = vuelo.buscarAsiento(noAsiento);
        
        if (a == null) {
            JOptionPane.showMessageDialog(null, "Asiento no existe.");
            return null;
        }
        if (a.getDisponibilidad() == 0) { // 0 significa ocupado
            JOptionPane.showMessageDialog(null, "El asiento ya está ocupado.");
            return null;
        }
        return a;
    }

    public void mostrarVuelos(Aeropuerto aeropuerto) {
        String lista = "--- VUELOS DISPONIBLES ---\n";
        
        for (int i = 0; i < aeropuerto.getIndAerolineas(); i++) {
            Aerolinea a = aeropuerto.getAerolineas()[i];
            if(a != null){
                lista += "\nAerolínea: " + a.getNombre() + "\n";
                
                Vuelo[] vuelos = a.getVuelosDisponibles();
                for (int j = 0; j < vuelos.length; j++) {
                    lista += "   " + vuelos[j].toString() + "\n";
                }
            }
        }
        // Impresión simple, sin librerías raras
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarAsientos(Vuelo vuelo) {
        String lista = "--- ASIENTOS DEL VUELO " + vuelo.getNoVuelo() + " ---\n";
        Asiento[] asientos = vuelo.getAsientos();
        
        for (int i = 0; i < asientos.length; i++) {
            if (asientos[i] != null) {
                lista += asientos[i].toString() + "\n";
            }
        }
        // Impresión simple
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarNomina(Aeropuerto aeropuerto) {
        String lista = "--- NÓMINA ---\n";
        Empleado[] empleados = aeropuerto.getEmpleados();
        
        for (int i = 0; i < aeropuerto.getIndEmpleados(); i++) {
            Empleado e = empleados[i];
            e.SueldoNeto(); 
            lista += e.getNombre() + " | Sueldo: $" + e.getSueldo() + "\n";
        }
        // Impresión simple
        JOptionPane.showMessageDialog(null, lista);
    }

    public int verificarVuelos(Aeropuerto aeropuerto) {
        if (aeropuerto.getIndVuelos() == 0) {
            JOptionPane.showMessageDialog(null, "No hay vuelos registrados.");
            return 0;
        }
        return 1;
    }

    // --- CARGA DE DATOS INICIALES ---
    public Aeropuerto datosInicializados() {
        Aeropuerto aeropuerto = new Aeropuerto("Benito Juárez", "CDMX");

        // Aerolíneas
        aeropuerto.addAerolinea("AeroMexico"); 
        aeropuerto.addAerolinea("Volaris");     

        // 3 Agentes de Mostrador
        for (int i = 0; i < 3; i++) {
            AgenteMostrador agente = new AgenteMostrador(1000L + i, "Agente " + (i + 1), 2, 35000.0f);
            aeropuerto.addEmpleado(agente); 
        }

        // 1 Vuelo de prueba
        Vuelo v = new Vuelo(100L, "CDMX", "CUN", "08:00", "10:00", "2h");
        
        // Personal del vuelo
        Piloto p = new Piloto(5000, 1, v, 2000L, "Capitán Juan", 10, 120000f);
        Azafata az = new Azafata(0, v, 3000L, "Azafata Ana", 3, 45000f);

        p.setVueloAsignado(v);
        az.setVueloAsignado(v);

        aeropuerto.addVuelo(v, 0); 
        aeropuerto.addEmpleado(p);
        aeropuerto.addEmpleado(az);

        return aeropuerto;
    }
}
