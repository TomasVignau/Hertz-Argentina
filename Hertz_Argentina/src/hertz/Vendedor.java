package hertz;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Vendedor extends Usuario implements Serializable {

    final static int PRECIO_POR_LITRO_COMBUSTIBLE = 1300;

    private float cantRecaudada;

    public float getCantRecaudada() {
        return cantRecaudada;
    }

    public void setCantRecaudada(float cantRecaudada) {
        this.cantRecaudada = cantRecaudada;
    }

    @Override
    public boolean proceder(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("Bienvenido/a Vendedor/a");
        char opc;
        boolean seguir = true;
        do {
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL VENDEDOR\n"
                    + "[1] Proceso cliente\n"
                    + "[2] Proceso reserva\n"
                    + "[3] Devolver una reserva\n"
                    + "[4] Salir de este menu\n"
                    + "[5] Salir del sistema");
            Validador f = new Validador();

            switch (opc) {
                case '1':
                    EntradaSalida.mostrarString("-- PROCESOS CLIENTES --");
                    char opcion3 = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta un cliente\n"
                            + "[2]Dar de baja un cliente\n"
                            + "[3]Modificar un cliente\n"
                            + "[4]Volver");

                    opcion3 = f.validarNumIngresado('1', '4', opcion3, "ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta un cliente\n"
                            + "[2]Dar de baja un cliente\n"
                            + "[3]Modificar un cliente\n"
                            + "[4]Volver");

                    if (opcion3 == '1') {
                        f.darDeAltaCLiente(sistemaDeReserva);
                    } else if (opcion3 == '2') {
                        f.darDeBajaCliente(sistemaDeReserva);
                    } else if (opcion3 == '3') {
                        f.modificarCliente(sistemaDeReserva);
                    }
                    break;
                case '2':
                    EntradaSalida.mostrarString("-- PROCESOS RESERVAS --");
                    char opcion2 = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta una reserva\n"
                            + "[2]Dar de baja una reserva\n"
                            + "[3]Volver");

                    opcion2 = f.validarNumIngresado('1', '3', opcion2, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta una reserva\n"
                            + "[2]Dar de baja una reserva\n"
                            + "[3]Volver"));
                    if (opcion2 == '1') {
                        EntradaSalida.mostrarString("-- DAR DE ALTA UNA RESERVA --\n");
                        EntradaSalida.mostrarString("*A continuacion, deberas ingresar los datos de la reserva*\n");
                        Reserva reserva = new Reserva();
                        String codReserva = (sistemaDeReserva.getReservas().size() + 1) + "";
                        reserva.setCodDeReserva(codReserva);

                        for (Oficina ofi : sistemaDeReserva.getOficinas()) {
                            EntradaSalida.mostrarString("\n" + ofi.toString());
                        }

                        String codOficinaAAsignar = EntradaSalida.leerString("Ingrese el codigo de oficina donde se esta realizando la reserva: ");
                        int idxOfi = 0;
                        boolean encontrado = false;
                        while (!encontrado) {
                            idxOfi = 0;
                            while (idxOfi < sistemaDeReserva.getOficinas().size() && !(sistemaDeReserva.getOficinas().get(idxOfi).getCodOficina().equals(codOficinaAAsignar))) {
                                idxOfi++;
                            }
                            if (idxOfi == sistemaDeReserva.getOficinas().size()) {
                                EntradaSalida.mostrarString("No existe la oficina.\n");
                                codOficinaAAsignar = EntradaSalida.leerString("Ingrese nuevamente el codigo de la oficina.");
                            } else {
                                encontrado = true;
                                reserva.setOficinaDeRetiro(sistemaDeReserva.getOficinas().get(idxOfi));
                            }
                        }

                        char opcionModoReserva = EntradaSalida.leerChar("Por que medio se esta realizando la reserva?\n"
                                + "[1]Presencial\n"
                                + "[2]Telefono\n"
                                + "Ingrese la opcion: ");

                        if (opcionModoReserva == '1') {
                            reserva.setMedioPorDondeSeRealizaLaReserva("Presencial");
                        } else {
                            reserva.setMedioPorDondeSeRealizaLaReserva("Telefono");
                        }

                        for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
                            if (sistemaDeReserva.getUsuarios().get(i) instanceof Cliente) {
                                EntradaSalida.mostrarString("->" + sistemaDeReserva.getUsuarios().get(i).toString() + "\n");
                            }
                        }

                        String codDeClienteAAsignarReserva = EntradaSalida.leerString("Ingrese el codigo de cliente al que le va a asignar la reserva:");
                        int idx = 0;
                        boolean confirmacion = false;
                        while (idx < sistemaDeReserva.getUsuarios().size() && !confirmacion) {
                            if (sistemaDeReserva.getUsuarios().get(idx) instanceof Cliente) {
                                Cliente cliEncontrado = (Cliente) sistemaDeReserva.getUsuarios().get(idx);
                                if (codDeClienteAAsignarReserva.equals(cliEncontrado.getCodCliente())) {
                                    EntradaSalida.mostrarString("\nEl cliente al que le asignaras la reserva es el siguiente\n ->" + sistemaDeReserva.getUsuarios().get(idx).toString() + "\n");
                                    confirmacion = EntradaSalida.leerBoolean("Esta seguro que desea asignaras la reserva al cliente <<" + codDeClienteAAsignarReserva + ">>");
                                    if (confirmacion) {
                                        reserva.setCliente((Cliente) sistemaDeReserva.getUsuarios().get(idx));
                                    } else {
                                        EntradaSalida.mostrarString("\nEl cliente no se asigno a la reserva. Elija otro a continuacion.");
                                        codDeClienteAAsignarReserva = EntradaSalida.leerString("\nIngrese el codigo de cliente al que le va a asignar la reserva:");
                                    }
                                }
                            }

                            idx = idx + 1;
                        }

                        reserva.setFechaInicio(EntradaSalida.leerDate("Fecha de Inicio de la reserva [dia/mes/anio]: "));
                        reserva.setFechaFinal(EntradaSalida.leerDate("Fecha de Fin de la reserva [dia/mes/anio]: "));

                        while (reserva.getFechaFinal().compareTo(reserva.getFechaInicio()) < 0) {
                            reserva.setFechaFinal(EntradaSalida.leerDate("Error. La fecha de fin de la reserva debe ser mayor a la de inicio\n"
                                    + "Fecha de Fin de la reserva [dia/mes/anio]: "));
                        }

                        EntradaSalida.mostrarString("\nA continuacion, se desplegara la lista de vehiculos disponibles en ese periodo de tiempo. ");

                        ArrayList<Vehiculo> vehiculosDisponibles = new ArrayList<>();

                        // Iterar sobre los vehículos del sistema de reservas
                        for (Vehiculo vehiculo : sistemaDeReserva.getVehiculos()) {
                            boolean disponible = true;
                            // Verificar si el vehículo está reservado en algún momento dentro del intervalo de fechas de la nueva reserva
                            for (Reserva reservaExistente : sistemaDeReserva.getReservas()) {
                                // Iterar sobre los vehículos reservados en esta reserva
                                for (Vehiculo vehiculoReservado : reservaExistente.getVehiculos()) {
                                    // Verificar si es el mismo vehículo
                                    if (vehiculo.getPatente().equals(vehiculoReservado.getPatente())) {
                                        // Verificar si las fechas se superponen
                                        if (!(reservaExistente.getFechaFinal().before(reserva.getFechaInicio())
                                                || reservaExistente.getFechaInicio().after(reserva.getFechaFinal()))) {
                                            disponible = false;
                                            break;  // El vehículo no está disponible en este intervalo
                                        }
                                    }
                                }

                                if (!disponible) {
                                    break;  // El vehículo no está disponible
                                }
                            }

                            // Si el vehículo no está reservado en el intervalo de fechas, agregarlo a vehiculosDisponibles
                            if (disponible) {
                                vehiculosDisponibles.add(vehiculo);
                            }
                        }
                        boolean seguirEligiendo = false;
                        boolean opcCorrecta = false;
                        String patenteElegida = null;

                        int contadorVehiculoBuscado = 0;
                        do {

                            EntradaSalida.mostrarString("Los autos para la reserva son los siguientes...\n");
                            opcCorrecta = false;
                            while (!opcCorrecta) {
                                for (Vehiculo vD : vehiculosDisponibles) {
                                    EntradaSalida.mostrarString(vD.toString() + "\n");
                                }
                                contadorVehiculoBuscado = 0;

                                patenteElegida = EntradaSalida.leerString("\nIngrese la patente del vehiculo:");

                                while (contadorVehiculoBuscado < vehiculosDisponibles.size() && !(patenteElegida.equals(vehiculosDisponibles.get(contadorVehiculoBuscado).getPatente()))) {
                                    contadorVehiculoBuscado++;
                                }

                                if (contadorVehiculoBuscado == vehiculosDisponibles.size()) {
                                    EntradaSalida.mostrarString("No existe ese vehiculo en el sistema\n");
                                    opcCorrecta = false;
                                } else {
                                    opcCorrecta = true;
                                    vehiculosDisponibles.get(contadorVehiculoBuscado).setLitrosDeGasolinaAlIniciarLaReserva(EntradaSalida.leerFloat("Litros de gasolina: "));
                                    reserva.getVehiculos().add(vehiculosDisponibles.get(contadorVehiculoBuscado));
                                    vehiculosDisponibles.get(contadorVehiculoBuscado).setEntregado(true);
                                    vehiculosDisponibles.remove(contadorVehiculoBuscado);
                                    EntradaSalida.mostrarString("*Vehiculo agregado a la reserva*\n");
                                }
                            }
                            seguirEligiendo = EntradaSalida.leerBoolean("Desea elegir otro vehiculo?");
                        } while (seguirEligiendo);

                        reserva.setPrecioAlquilerVehiculo(vehiculosDisponibles.get(contadorVehiculoBuscado).getPrecio());

                        int milisegundosPorDia = 86400000;
                        int cantDiasDeLaReserva = (int) ((reserva.getFechaFinal().getTime() - reserva.getFechaInicio().getTime()) / milisegundosPorDia);
                        reserva.setPrecioTotal(reserva.getPrecioAlquilerVehiculo() * cantDiasDeLaReserva);
                        reserva.setOficinaDeRetiro(sistemaDeReserva.getVehiculos().get(contadorVehiculoBuscado).getOficinaInicial());
                        sistemaDeReserva.getReservas().add(reserva);
                        float recaudadoHastaElMomento = this.getCantRecaudada();
                        this.setCantRecaudada(reserva.getPrecioTotal() + recaudadoHastaElMomento);
                        EntradaSalida.mostrarString("\n*Se ha agregado la reserva al sistema*\n");

                    } else if (opcion2 == '2') {
                        EntradaSalida.mostrarString("-- DAR DE BAJA UNA RESERVA --\n");
                        String codigoReservaADarDeBaja;

                        if (sistemaDeReserva.getReservas().size() != 0) {
                            for (Reserva r : sistemaDeReserva.getReservas()) {
                                EntradaSalida.mostrarString(r.toString() + "\n");
                            }
                            int i = 0;
                            codigoReservaADarDeBaja = EntradaSalida.leerString("\nIngrese el codigo de reserva que quiere dar de baja: ");
                            while (i < sistemaDeReserva.getReservas().size() && !(sistemaDeReserva.getReservas().get(i).getCodDeReserva().equals(codigoReservaADarDeBaja))) {
                                i++;
                            }

                            if (i == sistemaDeReserva.getReservas().size()) {
                                EntradaSalida.mostrarString("\nEl codigo de reserva ingresado no es valido");
                            } else {
                                if (EntradaSalida.leerBoolean("\nEsta seguro que desea dar de baja la reserva?")) {
                                    sistemaDeReserva.getReservas().remove(i);
                                    EntradaSalida.mostrarString("\n*La reserva ha sido eliminada exitosamente*\n");
                                }
                            }
                        } else {
                            EntradaSalida.mostrarString("No se encuentran reservas en el sitema.\n");
                        }

                    }
                    break;
                case '3':
                    EntradaSalida.mostrarString("--- DEVOLUCION DE RESERVA ---\n");

                    ArrayList<Reserva> reservasDeXUsuario = new ArrayList<>();

                    String codigoClienteDevolucion = EntradaSalida.leerString("Ingrese el codigo de cliente que va a realizar la devolucion: ");

                    for (Reserva r : sistemaDeReserva.getReservas()) {
                        if (r.getCliente().getCodCliente().equals(codigoClienteDevolucion)) {
                            EntradaSalida.mostrarString(r.toString());
                            reservasDeXUsuario.add(r);
                        }
                    }

                    String codigoDeReservaDevolucion = EntradaSalida.leerString("Ingrese el codigo de la reserva que va a realizar la devolucion: ");
                    int i = 0;
                    while (i < reservasDeXUsuario.size() && !(codigoDeReservaDevolucion.equals(reservasDeXUsuario.get(i).getCodDeReserva()))) {
                        i++;
                    }

                    if (i == reservasDeXUsuario.size()) {
                        EntradaSalida.mostrarString("\nNo existe la reserva");
                    } else {

                        for (Vehiculo v : reservasDeXUsuario.get(i).getVehiculos()) {
                            EntradaSalida.mostrarString(v.toString() + "\n");
                            v.setLitrosDeGasolinaAlFinalizarLaReserva(EntradaSalida.leerFloat("Ingrese la cantidad de litros de combustible con los que se devuelve el vehiculo: "));
                            if (v.getLitrosDeGasolinaAlFinalizarLaReserva() < v.getLitrosDeGasolinaAlIniciarLaReserva()) {
                                EntradaSalida.mostrarString("Los litros de gasolina son menores a los que tenia el vehiculo cuando fue entregado.\n"
                                        + "A continuacion le diremos el importe aniadido.\n");
                                float cantLitrosACobrar = v.getLitrosDeGasolinaAlIniciarLaReserva() - v.getLitrosDeGasolinaAlFinalizarLaReserva();
                                float importeAAniadir = cantLitrosACobrar * PRECIO_POR_LITRO_COMBUSTIBLE;
                                EntradaSalida.mostrarString("Importe extra: \n" + importeAAniadir);
                                reservasDeXUsuario.get(i).setPrecioTotal(reservasDeXUsuario.get(i).getPrecioTotal() + importeAAniadir);
                                EntradaSalida.mostrarString("Datos de la reserva actualizados: \n" + reservasDeXUsuario.get(i).toString());
                            } else {
                                EntradaSalida.mostrarString("Datos de la reserva: \n");
                                EntradaSalida.mostrarString(reservasDeXUsuario.get(i).toString());
                            }
                        }
                        int j = 0;
                        while (!(reservasDeXUsuario.get(i).getCodDeReserva().equals(sistemaDeReserva.getReservas().get(j).getCodDeReserva()))) {
                            j++;
                        }
                        sistemaDeReserva.getReservas().remove(j);
                        EntradaSalida.mostrarString("\n*La reserva ha sido devuelta*");
                    }

                    break;

                case '4':
                    seguir = true;
                    break;
                case '5':
                    seguir = false;
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            if (opc >= '1' && opc <= '3') {
                try {
                    sistemaDeReserva.serializar("hertz.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } while (opc != '4' && opc != '5');
        return seguir;
    }

    @Override
    public String toString() {
        return "Usuario: " + this.getUsuario() + " Password: " + this.getPassword() + " Cantidad Recaudada: " + this.getCantRecaudada();
    }
}
