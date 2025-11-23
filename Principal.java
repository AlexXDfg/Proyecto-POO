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
                "4. Ver Empleados\n" +
                "5. Ver Aerolineas\n" +
                "6. Vender Boleto\n" +
                "7. Verificar Estado (Vuelo/Asiento)\n" +
                "8. ADMINISTRACIÓN (Agregar Vuelos/Empleados/Aerolíneas)\n"+
                "9. Guardar y Salir\n" +
                "--------------------------------\n" +
                "Seleccione una opción:"
            );
            opcion = Integer.parseInt(entrada);

            switch(opcion) {
                case 1: // Ver Aeropuerto
                    JOptionPane.showMessageDialog(null, aeropuerto.toString());
                    break;

                case 2: // Ver Vuelos
                    if (verificarVuelos(aeropuerto) == 1) 
                        mostrarVuelos(aeropuerto);
                    break;

                case 3: // Ver Nómina
                    mostrarNomina(aeropuerto);
                    break;

                case 4: // Ver Empleados
                    mostrarEmpleados(aeropuerto);
                    break;

                case 5: // Ver Aerolineas
                    mostrarAerolineas(aeropuerto);
                    break;

                case 6: // Vender Boleto
                    venderBoleto(aeropuerto);
                    break;

                case 7: // Verificar Estado (Interface)
                    verificarEstado(aeropuerto);
                    break;

                case 8: // Administración
                    menuAdministracion(aeropuerto);
                    break;
                    
                case 9: // Guardar y Salir
                    mensaje.detener();
                    Persistencia.guardaPersistencia(aeropuerto);
                    JOptionPane.showMessageDialog(null, "Guardando y saliendo...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }

        } while(opcion != 9);
    }

    public void menuAdministracion(Aeropuerto aeropuerto){
        String opcAdmin = JOptionPane.showInputDialog(
            "--- ADMINISTRACIÓN ---\n" +
            "1. Agregar Nuevo Vuelo\n" +
            "2. Contratar Nuevo Empleado\n" +
            "3. Agregar Nueva Aerolínea\n" +
            "4. Volver"
        );
        
        switch (Integer.parseInt(opcAdmin)){
            case 1: // Agregar Vuelo
                nuevoVuelo(aeropuerto);
                break;
            case 2: // Agregar Empleado
                nuevoEmpleado(aeropuerto);
                break;
            case 3: // Agregar Aerolínea
                nuevaAerolinea(aeropuerto);
                break;
            case 4: // Volver
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opcion no valida");
                break;
        }
    }

    public void nuevoVuelo(Aeropuerto aeropuerto){
        // Seleccionar Aerolínea
        String listaAerolineas = "Seleccione el índice de la aerolínea:\n";
        for(int i=0; i<aeropuerto.getIndAerolineas(); i++) {
            listaAerolineas += i + ". " + aeropuerto.getAerolineas()[i].getNombre() + "\n";
        }
        int idxAero = Integer.parseInt(JOptionPane.showInputDialog(listaAerolineas));
        
        long noVuelo = Long.parseLong(JOptionPane.showInputDialog("Número de Vuelo:"));
        String origen = JOptionPane.showInputDialog("Origen:");
        String destino = JOptionPane.showInputDialog("Destino:");
        String salida = JOptionPane.showInputDialog("Hora Salida (HH:MM):");
        String llegada = JOptionPane.showInputDialog("Hora Llegada (HH:MM):");
        String duracion = JOptionPane.showInputDialog("Duración:");

        Vuelo nuevoVuelo = new Vuelo(noVuelo, origen, destino, salida, llegada, duracion);
        
        int resultado = aeropuerto.addVuelo(nuevoVuelo, idxAero);
        
        if (resultado == 1)
            JOptionPane.showMessageDialog(null, "Vuelo agregado exitosamente.");
        else
            JOptionPane.showMessageDialog(null, "Error: No se pudo agregar el vuelo (Límite alcanzado o error en índice).");
    }

    public void nuevaAerolinea(Aeropuerto aeropuerto){
        String nombre = JOptionPane.showInputDialog("Nombre de la nueva Aerolínea:");

        int resultado = aeropuerto.addAerolinea(nombre);
        
        if (resultado == 1)
            JOptionPane.showMessageDialog(null, "Aerolínea " + nombre + " agregada exitosamente.");
        else
            JOptionPane.showMessageDialog(null, "Error: No se pudo agregar la aerolínea (Límite alcanzado).");
    }

    public void nuevoEmpleado(Aeropuerto aeropuerto) {
        // Datos Generales de cualquier Empleado
        String nombre = JOptionPane.showInputDialog("Nombre del Empleado:");
        long id = Long.parseLong(JOptionPane.showInputDialog("ID del Empleado:"));
        int antiguedad = Integer.parseInt(JOptionPane.showInputDialog("Antigüedad (años):"));
        float sueldoBase = Float.parseFloat(JOptionPane.showInputDialog("Sueldo Base:"));

        // Seleccionar Tipo de Empleado
        String listaPuestos = JOptionPane.showInputDialog("Seleccione el puesto:\n" +
        "1. Piloto\n" +
        "2. Copiloto\n" +
        "3. Azafata\n" +
        "4. Agente Mostrador\n");
        
        int indEmp = Integer.parseInt(JOptionPane.showInputDialog(listaPuestos));

        Empleado nuevoEmpleado = null;

        switch (indEmp) {
            case 1: // Piloto
                int horas = Integer.parseInt(JOptionPane.showInputDialog("Horas de vuelo:"));
                int licencia = Integer.parseInt(JOptionPane.showInputDialog("¿Tiene licencia vigente?\n" +
                                                                            "[0] No\n" + 
                                                                            "[1] Si\n"));
                while(licencia < 0 || licencia > 1){
                    licencia = Integer.parseInt(JOptionPane.showInputDialog("Opcion no valida intente de nuevo\n" +
                                                                            "¿Tiene licencia vigente?\n" +
                                                                            "[0] No\n" + 
                                                                            "[1] Si\n"));
                }
                
                Vuelo vueloPiloto = seleccionarVuelo(aeropuerto);
                if (vueloPiloto == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un vuelo para el piloto.");
                    return;
                }
                nuevoEmpleado = new Piloto(horas, licencia, vueloPiloto, id, nombre, antiguedad, sueldoBase);
                break;
            case 2://Copiloto
                mostrarPilotos(aeropuerto);
                long idPiloto = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del Piloto supervisor:"));
                Empleado emp = aeropuerto.buscarEmpleado(idPiloto);

                if (emp != null){
                    Piloto pilotoSup = (Piloto) emp;
                    int licenciaCop = Integer.parseInt(JOptionPane.showInputDialog("¿Tiene licencia vigente?\n" +
                                                                            "[0] No\n" + 
                                                                            "[1] Si\n"));
                    while(licenciaCop < 0 || licenciaCop > 1){
                        licenciaCop = Integer.parseInt(JOptionPane.showInputDialog("Opcion no valida intente de nuevo\n" +
                                                                                "¿Tiene licencia vigente?\n" +
                                                                                "[0] No\n" + 
                                                                                "[1] Si\n"));
                    }

                    Piloto.Copiloto nuevoCopiloto = pilotoSup.new Copiloto(id, nombre, antiguedad, sueldoBase, licCop);
                    pilotoSup.setCopiloto(nuevoCopiloto);
                    nuevoEmpleado = nuevoCopiloto;
                }else{
                    JOptionPane.showMessageDialog(null, "Error: Piloto no encontrado o ID incorrecto.\nEl Copiloto debe asignarse a un Piloto existente.");
                    return;
                }
                break;

            case 3: // Azafata
                int productos = Integer.parseInt(JOptionPane.showInputDialog("Productos vendidos (Inicial):"));
                Vuelo vueloAzafata = seleccionarVuelo(aeropuerto);
                if (vueloAzafata == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un vuelo para la azafata.");
                    return;
                }
                nuevoEmpleado = new Azafata(productos, vueloAzafata, id, nombre, antiguedad, sueldoBase);
                break;

            case 4:
                nuevoEmpleado = new AgenteMostrador(id, nombre, antiguedad, sueldoBase);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Opcion no valida");
                return;
        }

        if (nuevoEmpleado != null) {
            int resultado = aeropuerto.addEmpleado(nuevoEmpleado);
            
            if (resultado == 1)
                JOptionPane.showMessageDialog(null, "Empleado " + nombre + " contratado exitosamente.");
            else
                JOptionPane.showMessageDialog(null, "Error: La nómina está llena (Máx 120 empleados).");
        }
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
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarAerolineas(Aeropuerto aeropuerto) {
        String lista = "--- AEROLÍNEAS ---\n";
        Aerolinea[] aerolineas = aeropuerto.getAerolineas();
        
        for (int i = 0; i < aeropuerto.getIndAerolineas(); i++) {
            Aerolinea a = aerolineas[i];
            lista += a.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarEmpleados(Aeropuerto aeropuerto) {
        String lista = "--- EMPLEADOS ---\n";
        Empleado[] empleados = aeropuerto.getEmpleados();
        
        for (int i = 0; i < aeropuerto.getIndEmpleados(); i++) {
            Empleado e = empleados[i];
            lista += e.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarPilotos(Aeropuerto aeropuerto) {
        String lista = "--- PILOTOS ---\n";
        Empleado[] empleados = aeropuerto.getEmpleados();
        
        for (int i = 0; i < aeropuerto.getIndEmpleados(); i++) {
            if(empleados[i] instanceof Piloto)
                lista += empleados[i].toString() + "\n";
        }
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
        JOptionPane.showMessageDialog(null, lista);
    }

    public void mostrarNomina(Aeropuerto aeropuerto) {
        String lista = "--- NÓMINA ---\n";
        Empleado[] empleados = aeropuerto.getEmpleados();
        
        for (int i = 0; i < aeropuerto.getIndEmpleados(); i++) {
            Empleado e = empleados[i];
            float sueldoNeto = e.SueldoNeto(); 
            lista += e.getNombre() + " | Sueldo final: $" + sueldoNeto + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public int verificarVuelos(Aeropuerto aeropuerto) {
        if (aeropuerto.getIndVuelos() == 0) {
            JOptionPane.showMessageDialog(null, "No hay vuelos registrados.");
            return 0;
        }
        return 1;
    }

    public void verificarEstado(Aeropuerto aeropuerto) {
        int seleccion = Integer.parseInt(JOptionPane.showInputDialog("¿Qué desea verificar?\n"+
                                                                    "[1] Vuelo\n"+
                                                                    "[2] Asiento\n"));
        if (seleccion == 1) { // Vuelo
            Vuelo v = seleccionarVuelo(aeropuerto);
            if (v != null)
                JOptionPane.showMessageDialog(null, "Estado: " + v.getEstadoDetallado());
        } else if (seleccion == 2) { // Asiento
            long idVuelo = Long.parseLong(JOptionPane.showInputDialog("ID del Vuelo:"));
            Vuelo v = aeropuerto.buscarVuelo(idVuelo);
            if (v != null) {
                Asiento a = seleccionarAsiento(v);
                if (a != null)
                    JOptionPane.showMessageDialog(null, "Estado: " + a.getEstadoDetallado());
            } else {
                JOptionPane.showMessageDialog(null, "Vuelo no encontrado.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Opcion no valida.");
        }
    }

    public void venderBoleto(Aeropuerto aeropuerto){
        if (verificarVuelos(aeropuerto) == 0) return;
        
        mostrarVuelos(aeropuerto);
        Vuelo vueloSel = seleccionarVuelo(aeropuerto);
        if (vueloSel == null) return;

        mostrarAsientos(vueloSel);
        Asiento asientoSel = seleccionarAsiento(vueloSel);
        if (asientoSel == null) return;

        Pasajero pasajero = crearPasajero();
        
        long idAgente = Long.parseLong(JOptionPane.showInputDialog("ID del Agente (Ej. 1000):"));
        float precio = Float.parseFloat(JOptionPane.showInputDialog("Precio del boleto:"));
        long noBoleto = System.currentTimeMillis();

        Boleto boleto = aeropuerto.gestionarVentaBoleto(idAgente, vueloSel.getNoVuelo(), asientoSel.getNoAsiento(), pasajero, noBoleto, precio); //

        if (boleto != null) {
            JOptionPane.showMessageDialog(null, "¡Venta Exitosa!\n" + boleto.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Error: ID Agente inválido o asiento ocupado.");
        }
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
        Piloto.Copiloto c = p.new Copiloto(2001L, "Copiloto Luis", 5, 80000f, 1);
        Azafata az = new Azafata(0, v, 3000L, "Azafata Ana", 3, 45000f);

        p.setCopiloto(c);
        p.setVueloAsignado(v);
        az.setVueloAsignado(v);

        aeropuerto.addVuelo(v, 0); 
        aeropuerto.addEmpleado(p);
        aeropuerto.addEmpleado(c);
        aeropuerto.addEmpleado(az);

        return aeropuerto;
    }
}




