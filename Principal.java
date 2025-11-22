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

        Aeropuerto persistencia = Persistencia.cargaArchivoPesistencia();

        if (persistencia != null) {
            aeropuerto = persistencia;
        }

        Mensaje mensaje = new Mensaje(aeropuerto);
        Thread mensajero = new Thread(mensaje);
        mensajero.start(); 
        
        do{
            String menu = "Seleccione una opción:\n"
                        + "[1] Ver Aeropuerto\n"
                        + "[2] Obtener estado detallado\n"
                        + "[3] Salir";
            String entrada = JOptionPane.showInputDialog(menu);
            
            // Manejo básico por si el usuario presiona "Cancelar"
            if (entrada == null) {
                opcion = 3;
            } else {
                try {
                    opcion = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    opcion = -1; // Opción inválida para forzar el default
                }
            }

            switch(opcion) {
                case 1:
                    // Ahora 'aeropuerto' está inicializado y se puede usar
                    JOptionPane.showMessageDialog(null, aeropuerto.toString());
                    break;
                case 2:
                    // Aquí iría la lógica para obtener el estado detallado
                    // Esta opción aún no está implementada en tu 'menu' original
                    JOptionPane.showMessageDialog(null, "Funcionalidad 'Estado Detallado' no implementada.");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            }
        }while(opcion != 3);
    }

    public void datosInicializados() {
        // 1. Crear la instancia principal del Aeropuerto
        aeropuerto = new Aeropuerto("Aeropuerto Internacional Benito Juárez", "Ciudad de México, México");

        // 2. Añadir las 3 Aerolíneas
        aeropuerto.addAerolinea("AeroMexico"); // Índice 0
        aeropuerto.addAerolinea("Volaris");     // Índice 1
        aeropuerto.addAerolinea("Viva Aerobus"); // Índice 2

        // 3. Añadir 30 Agentes de Mostrador (Empleados)
        // Usamos un ID base 1000 para Agentes
        for (int i = 0; i < 30; i++) {
            AgenteMostrador agente = new AgenteMostrador(
                1000L + i,                  // idEmpleado
                "Agente " + (i + 1),        // nombre
                (i % 5) + 1,                // antiguedad (1-5 años)
                35000.0f                    // sueldo base
            );
            aeropuerto.addEmpleado(agente); //
        }

        // Datos de ejemplo para vuelos
        String[] origenes = {"CDMX", "MTY", "CUN", "GDL", "TIJ"};
        String[] destinos = {"LAX", "JFK", "MIA", "BOG", "SCL", "EZE"};

        // 4. Bucle para crear 30 Vuelos, 30 Pilotos y 60 Azafatas
        for (int i = 0; i < 30; i++) {
            
            // --- A. Crear el Vuelo ---
            long noVuelo = 100L + i;
            String origen = origenes[i % origenes.length];
            String destino = destinos[i % destinos.length];
            // Asegurarnos que origen y destino no sean el mismo
            if (origen.equals(destino)) {
                destino = destinos[(i + 1) % destinos.length];
            }

            Vuelo vuelo = new Vuelo(
                noVuelo,                    // noVuelo
                origen,                     // origen
                destino,                    // destino
                String.format("%02d:00", (i % 24)), // horaSalida
                String.format("%02d:00", (i + 3) % 24), // horaLlegada
                "3h 0m"                     // duracion
            ); //
            
            // --- B. Crear el personal para ese vuelo (1 Piloto, 2 Azafatas) ---
            
            // Usamos ID base 2000 para Pilotos
            Piloto piloto = new Piloto(
                5000 + (i * 100),           // horasVuelo
                1,                          // licencia (1 = en regla)
                null,                       // vueloAsignado (se asigna después)
                2000L + i,                  // idEmpleado
                "Piloto " + (i + 1),        // nombre
                (i % 10) + 1,               // antiguedad (1-10 años)
                120000.0f                   // sueldo base
            ); //

            // Usamos ID base 3000 y 4000 para Azafatas
            Azafata azafata1 = new Azafata(
                0,                          // cantProductosVendidos
                null,                       // vueloAsignado (se asigna después)
                3000L + i,                  // idEmpleado
                "Azafata " + (i * 2 + 1),   // nombre
                (i % 4) + 1,                // antiguedad (1-4 años)
                45000.0f                    // sueldo base
            ); //

            Azafata azafata2 = new Azafata(
                0,
                null,
                4000L + i,
                "Azafata " + (i * 2 + 2),
                (i % 4) + 1,
                45000.0f
            ); //

            // --- C. Asignar el vuelo al personal ---
            piloto.setVueloAsignado(vuelo);     //
            azafata1.setVueloAsignado(vuelo);   //
            azafata2.setVueloAsignado(vuelo);

            // --- D. Añadir el Vuelo al Aeropuerto (y a su Aerolínea correspondiente) ---
            // Los vuelos 0-9 van a la aerolínea 0
            // Los vuelos 10-19 van a la aerolínea 1
            // Los vuelos 20-29 van a la aerolínea 2
            int indiceAerolinea = i / 10;
            aeropuerto.addVuelo(vuelo, indiceAerolinea); //

            // --- E. Añadir el personal al Aeropuerto ---
            aeropuerto.addEmpleado(piloto);
            aeropuerto.addEmpleado(azafata1);
            aeropuerto.addEmpleado(azafata2);
        }
    }
}

