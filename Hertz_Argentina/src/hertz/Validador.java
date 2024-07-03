package hertz;

public class Validador {

    public void darDeAltaCLiente(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("-- DAR DE ALTA UN CLIENTE --\n");
        EntradaSalida.mostrarString("*A continuacion, deberas ingresar los datos del cliente*\n");
        int ultimoCodCliente = 0;
        Cliente cliente = new Cliente();

        for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
            if (sistemaDeReserva.getUsuarios().get(i) instanceof Cliente) {
                Cliente cli = new Cliente();
                cli = (Cliente) sistemaDeReserva.getUsuarios().get(i);
                ultimoCodCliente = Integer.parseInt(cli.getCodCliente());
            }
        }

        String codigoDelCliente = (ultimoCodCliente + 1) + "";

        cliente.setCodCliente(codigoDelCliente);
        EntradaSalida.mostrarString("Codigo del Cliente: " + codigoDelCliente);
        cliente.setNombre(EntradaSalida.leerString("Nombre del cliente:"));
        cliente.setDni(validarDNI(EntradaSalida.leerString("DNI del cliente:")));

        String telefonoCliente = EntradaSalida.leerString("Telefono del cliente: ");
        telefonoCliente = validarTelefono(telefonoCliente, "ERROR. Telefono del cliente: ");
        cliente.setTelefono(telefonoCliente);

        cliente.setDireccion(EntradaSalida.leerString("Direccion del cliente:"));
        cliente.setUsuario(verificarRepeticionDeUsuario(EntradaSalida.leerString("Usuario del cliente:"), sistemaDeReserva));
        cliente.setPassword(EntradaSalida.leerString("Password del cliente:"));
        sistemaDeReserva.getUsuarios().add(cliente);
        EntradaSalida.mostrarString("\n*Se ha agregado el cliente al sistema*\n");
    }

    public void darDeBajaCliente(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("-- DAR DE BAJA UN CLIENTE --\n");
        int cantClientes = 0;
        for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
            if (sistemaDeReserva.getUsuarios().get(i) instanceof Cliente) {
                cantClientes = cantClientes + 1;
            }
        }
        if (cantClientes == 0) {
            EntradaSalida.mostrarString("*NO hay clientes en el sistema para dar de baja*\n");
        } else {
            EntradaSalida.mostrarString("*Los clientes para dar de baja son los siguientes:*\n");
            for (int j = 0; j < sistemaDeReserva.getUsuarios().size(); j++) {
                if (sistemaDeReserva.getUsuarios().get(j) instanceof Cliente) {
                    EntradaSalida.mostrarString("->" + sistemaDeReserva.getUsuarios().get(j).toString() + "\n");
                }
            }

            EntradaSalida.mostrarString("*A continuacion, deberas ingresar el codigo del cliente*\n");
            String codCliente = EntradaSalida.leerString("Codigo Cliente: ");

            int i = 0;
            boolean encontrado = false;
            while (i < sistemaDeReserva.getUsuarios().size() && !encontrado) {
                if (sistemaDeReserva.getUsuarios().get(i) instanceof Cliente) {
                    Cliente clienteEncontrado = (Cliente) sistemaDeReserva.getUsuarios().get(i);
                    if (codCliente.equals(clienteEncontrado.getCodCliente())) {
                        encontrado = true;
                        EntradaSalida.mostrarString("\nEl cliente que daras de baja es el siguiente\n ->" + sistemaDeReserva.getUsuarios().get(i).toString() + "\n");
                        boolean confirmacion;
                        confirmacion = EntradaSalida.leerBoolean("Esta seguro que desea dar de baja el cliente <<" + codCliente + ">>");
                        if (confirmacion) {
                            sistemaDeReserva.getUsuarios().remove(i);
                            EntradaSalida.mostrarString("\n*Se ha eliminado el cliente del sistema*\n");
                        } else {
                            EntradaSalida.mostrarString("\n*NO se ha eliminado el cliente del sistema*\n");
                        }
                    }
                }
                i++;
            }

            if (!encontrado) {
                EntradaSalida.mostrarString("\nNo se ha encontrado el cliente, asegurate de haber ingresado bien el codigo del cliente.\n");
            }
        }
    }

    public void modificarCliente(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("-- MODIFICAR UN CLIENTE --");
        for (Usuario u : sistemaDeReserva.getUsuarios()) {
            if (u instanceof Cliente) {
                EntradaSalida.mostrarString("------------------------------\n"
                        + u.toString() + "\n---------------------------------\n");
            }
        }

        String codClienteAModificar = EntradaSalida.leerString("Ingrese el codigo de cliente a modificar: ");
        int i = 0;
        boolean encontrado = false;
        int idxCliEncontrado = 0;
        while (i < sistemaDeReserva.getUsuarios().size() && !encontrado) {
            if (sistemaDeReserva.getUsuarios().get(i) instanceof Cliente) {
                Cliente cli = new Cliente();
                cli = (Cliente) sistemaDeReserva.getUsuarios().get(i);
                if (codClienteAModificar.equals(cli.getCodCliente())) {
                    encontrado = true;
                    idxCliEncontrado = i;
                }
            }
            i++;
        }
        if (!encontrado) {
            EntradaSalida.mostrarString("El cliente no existe. Asegurate de haber ingresado bien el codigo del cliente");
        } else {
            Cliente c = new Cliente();
            c = (Cliente) sistemaDeReserva.getUsuarios().get(idxCliEncontrado);
            char opcionAModificar = EntradaSalida.leerChar("[1]Nombre\n"
                    + "[2]DNI\n"
                    + "[3]Direccion\n"
                    + "[4]Telefono\n"
                    + "Ingrese lo que quiere modificar: ");

            switch (opcionAModificar) {
                case '1':
                    EntradaSalida.mostrarString("Nombre Actual: " + c.getNombre());
                    c.setNombre(EntradaSalida.leerString("Nombre nuevo: "));
                    break;
                case '2':
                    EntradaSalida.mostrarString("DNI Actual: " + c.getDni());
                    c.setDni(validarDNI(EntradaSalida.leerString("DNI nuevo: ")));
                    break;
                case '3':
                    EntradaSalida.mostrarString("Direccion Actual: " + c.getDireccion());
                    c.setDireccion(EntradaSalida.leerString("Direccion nueva: "));
                    break;
                case '4':
                    EntradaSalida.mostrarString("Telefono Actual: " + c.getTelefono());
                    c.setTelefono(validarTelefono(EntradaSalida.leerString("Telefono nuevo: "), "ERROR. Telefono nuevo: "));
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opcionAModificar = '*';
            }
        }
    }

    public char validarNumIngresado(char minimo, char maximo, char ingresado, String mensaje) {
        while (ingresado < minimo || ingresado > maximo) {
            ingresado = EntradaSalida.leerChar(mensaje);
        }
        return ingresado;
    }

    public String validarTelefono(String telefono, String mensaje) {
        long tel;
        while (true) {
            try {
                tel = Long.parseLong(telefono);
                if (tel > 1100000000 && tel < 1199999999) {
                    break; // Salir del bucle si el teléfono es válido
                } else {
                    EntradaSalida.mostrarString("Numero de telefono fuera de rango. Debe estar entre 1100000000 y 1199999999.");
                }
            } catch (NumberFormatException e) {
                EntradaSalida.mostrarString("El numero de telefono ingresado no es valido. Debe contener solo numeros.");
            }
            telefono = EntradaSalida.leerString(mensaje);
        }

        return telefono;
    }

    public String verificarString(String s, String mensaje) {
        while (s.equals("")) {
            s = EntradaSalida.leerString(mensaje);
        }
        return s;
    }

    public String validarPatente(String patente) {
        // Expresión para validar una patente
        String regex = "^[A-Z]{3}\\d{3}$|^[A-Z]{2}\\d{3}[A-Z]{2}$";

        // Verificar si la patente cumple con el formato esperado
        while (!(patente.matches(regex))) {
            patente = EntradaSalida.leerString("ERROR FORMATO. Patente: ");
        }

        return patente;
    }

    public String verificarRepeticionDePatente(String patenteIngresada, SistemaDeReserva sistemaDeReserva) {
        int i = 0;
        boolean patenteEncontrada = true;
        int cantVeces = 0;
        while (patenteEncontrada) {
            if (cantVeces > 0) {
                patenteIngresada = EntradaSalida.leerString("*Ya existe la patente. Ingresala nuevamente.* "
                        + "\nPatente:");
                patenteIngresada = validarPatente(patenteIngresada);
            }
            i = 0;
            while (i < sistemaDeReserva.getVehiculos().size() && !(patenteIngresada.equals(sistemaDeReserva.getVehiculos().get(i).getPatente()))) {
                i++;
            }
            if (i == sistemaDeReserva.getVehiculos().size()) {
                patenteEncontrada = false;
            } else {
                patenteEncontrada = true;
            }
            cantVeces++;
        }
        return validarPatente(patenteIngresada);
    }

    public String verificarRepeticionDeUsuario(String usuarioIngresado, SistemaDeReserva sistemaDeReserva) {
        int i = 0;
        boolean usuarioEncontrado = true;
        int cantVeces = 0;
        while (usuarioEncontrado) {
            if (cantVeces > 0) {
                usuarioIngresado = EntradaSalida.leerString("*Ya existe el usuario. Ingresalo nuevamente.* "
                        + "\nUsuario:");
            }
            i = 0;
            while (i < sistemaDeReserva.getUsuarios().size() && !(usuarioIngresado.equals(sistemaDeReserva.getUsuarios().get(i).getUsuario()))) {
                i++;
            }
            if (i == sistemaDeReserva.getUsuarios().size()) {
                usuarioEncontrado = false;
            } else {
                usuarioEncontrado = true;
            }
            cantVeces++;
        }
        return usuarioIngresado;
    }

    public String validarDNI(String DNIIngresado) { 
        long dni;
        while (true) {
            try {
                dni = Long.parseLong(DNIIngresado);
                if (dni > 12000000 && dni < 47000000) {
                    break; // Salir del bucle si el teléfono es válido
                } else {
                    EntradaSalida.mostrarString("DNI no valido. Debe estar entre 12.000.000 y 47.000.000");
                }
            } catch (NumberFormatException e) {
                EntradaSalida.mostrarString("El numero de dni ingresado no es valido. Debe contener solo numeros.");
            }
            DNIIngresado = EntradaSalida.leerString("DNI valido: ");
        }

        return DNIIngresado;
    }
}
