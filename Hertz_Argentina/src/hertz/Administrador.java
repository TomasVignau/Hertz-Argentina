package hertz;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Administrador extends Usuario implements Serializable {

    final static int PRECIO_AUTO = 30000;
    final static int PRECIO_CAMIONETA = 75000;

    public Administrador(String u, String p) {
        setUsuario(u);
        setPassword(p);
    }

    @Override
    public String toString() {
        return "Usuario: " + this.getUsuario() + " Password: " + this.getPassword();
    }

    @Override
    public boolean proceder(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("Bienvenido/a Administrador/a");
        char opc;
        boolean seguir = true;

        do {
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL ADMINISTRADOR\n"
                    + "[1] Proceso de vehiculo\n"
                    + "[2] Proceso de vendedor\n"
                    + "[3] Proceso de administrador\n"
                    + "[4] Proceso de cliente\n"
                    + "[5] Proceso de oficina\n"
                    + "[6] Mostrar listado de vendedores ordenado por facturacion\n"
                    + "[7] Salir de este menu\n"
                    + "[8] Salir del sistema");
            Validador f = new Validador();

            switch (opc) {
                case '1':
                    EntradaSalida.mostrarString("-- PROCESOS VEHICULOS --");

                    char opcion = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta un vehiculo\n"
                            + "[2]Dar de baja un vehiculo \n"
                            + "[3]Modificar un vehiculo\n"
                            + "[4]Volver");

                    opcion = f.validarNumIngresado('1', '4', opcion, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta un vehiculo\n"
                            + "[2]Dar de baja un vehiculo \n"
                            + "[3]Modificar un vehiculo\n"
                            + "[4]Volver"));

                    if (opcion == '1') {
                        EntradaSalida.mostrarString("-- DAR DE ALTA UN VEHICULO --\n");
                        if (!(sistemaDeReserva.getOficinas().size() == 0)) {
                            EntradaSalida.mostrarString("*A continuacion, deberas ingresar los datos del vehiculo*\n");
                            Vehiculo vehiculo = new Vehiculo();
                            vehiculo.setPatente(f.verificarRepeticionDePatente(EntradaSalida.leerString("Patente: "), sistemaDeReserva));
                            vehiculo.setMarca(EntradaSalida.leerString("Marca: "));
                            vehiculo.setModelo(EntradaSalida.leerString("Modelo: "));
                            vehiculo.setColor(EntradaSalida.leerString("Color: "));
                            vehiculo.setEntregado(false);
                            int opcionCategoria;
                            EntradaSalida.mostrarString("\nCategorias del vehiculo"
                                    + "\n[1] Auto"
                                    + "\n[2] Camioneta\n");
                            do {
                                opcionCategoria = EntradaSalida.leerEntero("Categoria: ");
                            } while (opcionCategoria < 1 || opcionCategoria > 2);
                            if (opcionCategoria == 1) {
                                vehiculo.setPrecio(PRECIO_AUTO);
                            } else {
                                vehiculo.setPrecio(PRECIO_CAMIONETA);
                            }

                            EntradaSalida.mostrarString("Las oficinas para asignar el vehiculo son las siguientes:\n");

                            for (Oficina ofi : sistemaDeReserva.getOficinas()) {
                                EntradaSalida.mostrarString(ofi.toString() + "\n--------------------------------------------");
                            }

                            String codOficinaAAsignar = EntradaSalida.leerString("\nIngrese el codigo de oficina donde se encuentra el vehiculo: ");
                            int i = 0;
                            boolean encontrado = false;
                            while (!encontrado) {
                                i = 0;
                                while (i < sistemaDeReserva.getOficinas().size() && !(sistemaDeReserva.getOficinas().get(i).getCodOficina().equals(codOficinaAAsignar))) {
                                    i++;
                                }
                                if (i == sistemaDeReserva.getOficinas().size()) {
                                    EntradaSalida.mostrarString("No existe la oficina.\n");
                                    codOficinaAAsignar = EntradaSalida.leerString("Ingrese nuevamente el codigo de la oficina.");
                                } else {
                                    encontrado = true;
                                    vehiculo.setOficinaInicial(sistemaDeReserva.getOficinas().get(i));
                                }
                            }
                            sistemaDeReserva.getVehiculos().add(vehiculo);
                            EntradaSalida.mostrarString("\n*Se ha agregado el vehiculo al sistema*\n");
                        } else {
                            EntradaSalida.mostrarString("No hay oficinas en el sistema. Primero crea una antes de crear un vehiculo.\n");
                        }

                    } else if (opcion == '2') {
                        EntradaSalida.mostrarString("-- DAR DE BAJA UN VEHICULO --\n");
                        if (sistemaDeReserva.getVehiculos().size() == 0) {
                            EntradaSalida.mostrarString("*NO hay vehiculos en el sistema para dar de baja*\n");
                        } else {
                            EntradaSalida.mostrarString("*Los autos para dar de baja son los siguientes:*\n");

                            for (Vehiculo v : sistemaDeReserva.getVehiculos()) {
                                EntradaSalida.mostrarString("->" + v.toString() + "\n");
                            }

                            EntradaSalida.mostrarString("*A continuacion, deberas ingresar la patente del vehiculo*\n");
                            String patente = f.validarPatente(EntradaSalida.leerString("Patente: "));
                            int i = 0;

                            while (i < sistemaDeReserva.getVehiculos().size() && !patente.equals(sistemaDeReserva.getVehiculos().get(i).getPatente())) {
                                i++;
                            }
                            if (i == sistemaDeReserva.getVehiculos().size()) {
                                EntradaSalida.mostrarString("\nNo se ha encontrado el vehiculo, asegurate de haber ingresado bien la patente.\n");
                            } else {
                                EntradaSalida.mostrarString("\nEl vehiculo que daras de baja es el siguiente\n ->" + sistemaDeReserva.getVehiculos().get(i).toString() + "\n");
                                boolean confirmacion;
                                confirmacion = EntradaSalida.leerBoolean("Esta seguro que desea dar de baja el vehiculo de patente <<" + patente + ">>");
                                if (confirmacion) {
                                    sistemaDeReserva.getVehiculos().remove(i);
                                    EntradaSalida.mostrarString("\n*Se ha eliminado el vehiculo del sistema*\n");
                                } else {
                                    EntradaSalida.mostrarString("\n*NO se ha eliminado el vehiculo del sistema*\n");
                                }
                            }
                        }
                    } else if (opcion == '3') {
                        EntradaSalida.mostrarString("-- MODIFICAR UN VEHICULO --\n");

                        if (sistemaDeReserva.getVehiculos().size() == 0) {
                            EntradaSalida.mostrarString("*NO hay vehiculos en el sistema para modificar*\n");
                        } else {

                            for (Vehiculo v : sistemaDeReserva.getVehiculos()) {
                                EntradaSalida.mostrarString(v.toString() + "\n---------------------------------\n");
                            }

                            String patenteAModificar = EntradaSalida.leerString("Ingrese la patente del vehiculo a modificar: ");
                            int i = 0;
                            boolean encontrado = false;
                            while (i < sistemaDeReserva.getVehiculos().size() && !encontrado) {
                                if (patenteAModificar.equals(sistemaDeReserva.getVehiculos().get(i).getPatente())) {
                                    encontrado = true;
                                } else {
                                    i++;
                                }
                            }
                            if (!encontrado) {
                                EntradaSalida.mostrarString("El vehiculo no existe. Asegurate de haber ingresado bien la patente del vehiculo");
                            } else {
                                Vehiculo v = new Vehiculo();
                                v = sistemaDeReserva.getVehiculos().get(i);
                                char opcionAModificar = EntradaSalida.leerChar("[1]Marca\n"
                                        + "[2]Modelo\n"
                                        + "[3]Color\n"
                                        + "[4]Oficina Inicial\n"
                                        + "Ingrese lo que quiere modificar: ");

                                switch (opcionAModificar) {
                                    case '1':
                                        EntradaSalida.mostrarString("Marca Actual: " + v.getMarca());
                                        v.setMarca(EntradaSalida.leerString("Marca nueva: "));
                                        break;
                                    case '2':
                                        EntradaSalida.mostrarString("Modelo Actual: " + v.getModelo());
                                        v.setModelo(EntradaSalida.leerString("Modelo nuevo: "));
                                        break;
                                    case '3':
                                        EntradaSalida.mostrarString("Color Actual: " + v.getColor());
                                        v.setColor(EntradaSalida.leerString("Color nuevo: "));
                                        break;
                                    case '4':
                                        EntradaSalida.mostrarString("\nOficina Inicial Actual: " + v.getOficinaInicial() + "\n");

                                        for (Oficina o : sistemaDeReserva.getOficinas()) {
                                            if (!(v.getOficinaInicial().getCodOficina().equals(o.getCodOficina()))) {
                                                EntradaSalida.mostrarString("------------------------------\n"
                                                        + o.toString() + "\n---------------------------------\n");
                                            }
                                        }

                                        boolean encuentreOficina = false;
                                        while (!encuentreOficina) {
                                            String codOficinaNuevo = EntradaSalida.leerString("Ingrese el codigo de la nueva oficina: ");
                                            int j = 0;
                                            while (j < sistemaDeReserva.getOficinas().size() && !(codOficinaNuevo.equals(sistemaDeReserva.getOficinas().get(j).getCodOficina()))) {
                                                j++;
                                            }

                                            if (j == sistemaDeReserva.getOficinas().size()) {
                                                EntradaSalida.mostrarString("No existe la oficina");
                                            } else {
                                                encuentreOficina = true;
                                                v.setOficinaInicial(sistemaDeReserva.getOficinas().get(j));
                                                EntradaSalida.mostrarString("\nOficina Inicial nueva: " + v.getOficinaInicial().toString());
                                            }
                                        }

                                        break;
                                    default:
                                        EntradaSalida.mostrarString("ERROR: Opcion invalida");
                                        opcionAModificar = '*';
                                }
                            }
                        }
                    }

                    break;
                case '2':
                    EntradaSalida.mostrarString("-- PROCESOS VENDEDORES --");
                    char opcion1 = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta un vendedor\n"
                            + "[2]Dar de baja un vendedor\n"
                            + "[3]Volver");

                    opcion1 = f.validarNumIngresado('1', '3', opcion1, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta un vendedor\n"
                            + "[2]Dar de baja un vendedor\n"
                            + "[3]Volver"));

                    if (opcion1 == '1') {
                        EntradaSalida.mostrarString("-- DAR DE ALTA UN VENDEDOR --\n"
                                + "*A continuacion, deberas ingresar los datos del vendedor*\n");
                        Vendedor vendedor = new Vendedor();
                        vendedor.setUsuario(f.verificarRepeticionDeUsuario(EntradaSalida.leerString("Usuario: "), sistemaDeReserva));
                        vendedor.setPassword(EntradaSalida.leerString("Clave: "));
                        vendedor.setCantRecaudada(0);
                        sistemaDeReserva.getUsuarios().add(vendedor);
                        EntradaSalida.mostrarString("\n*Se ha agregado el vendedor al sistema*\n");
                    } else if (opcion1 == '2') {
                        EntradaSalida.mostrarString("-- DAR DE BAJA UN VENDEDOR --\n");
                        if (sistemaDeReserva.getUsuarios().size() < 2) {
                            EntradaSalida.mostrarString("*NO hay vendedores en el sistema para dar de baja*\n");
                        } else {
                            EntradaSalida.mostrarString("*Los vendedores para dar de baja son los siguientes:*\n");
                            for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
                                if (sistemaDeReserva.getUsuarios().get(i) instanceof Vendedor) {
                                    EntradaSalida.mostrarString("->" + sistemaDeReserva.getUsuarios().get(i).toString());
                                }
                            }

                            EntradaSalida.mostrarString("*A continuacion, deberas ingresar el usuario del vendedor a dar de baja*\n");
                            String usuario = EntradaSalida.leerString("Usuario: ");

                            int i = 1;//Arranca en 1, porque el primero es el ADMINISTRADOR.

                            while (i < sistemaDeReserva.getUsuarios().size() && !usuario.equals(sistemaDeReserva.getUsuarios().get(i).getUsuario())) {
                                i++;
                            }
                            if (i == sistemaDeReserva.getUsuarios().size()) {
                                EntradaSalida.mostrarString("\nNo se ha encontrado el vendedor, asegurate de haber ingresado bien el usuario.\n");
                            } else {
                                EntradaSalida.mostrarString("\nEl usuario que daras de baja es el siguiente\n ->" + sistemaDeReserva.getUsuarios().get(i).getUsuario() + "\n");
                                boolean confirmacion;
                                confirmacion = EntradaSalida.leerBoolean("Esta seguro que desea dar de baja al Vendedor de usuario <<" + usuario + ">>");
                                if (confirmacion) {
                                    sistemaDeReserva.getUsuarios().remove(i);
                                    EntradaSalida.mostrarString("\n*Se ha eliminado el vendedor del sistema*\n");
                                } else {
                                    EntradaSalida.mostrarString("\n*NO se ha eliminado el vendedor del sistema*\n");
                                }
                            }
                        }
                    }
                    break;
                case '3':
                    EntradaSalida.mostrarString("-- PROCESOS ADMINISTRADORES --");
                    char opcion2 = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta un administrador\n"
                            + "[2]Dar de baja un administrador\n"
                            + "[3]Volver");

                    opcion2 = f.validarNumIngresado('1', '3', opcion2, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta un administrador\n"
                            + "[2]Dar de baja un administrador\n"
                            + "[3]Volver"));

                    if (opcion2 == '1') {
                        EntradaSalida.mostrarString("-- DAR DE ALTA UN ADMINISTRADOR --\n");
                        EntradaSalida.mostrarString("*A continuacion, deberas ingresar los datos del administrador*\n");
                        String usuario;
                        String password;
                        int idxBuscarSiExisteUsuario = 0;
                        boolean encontrado = false;
                        usuario = f.verificarRepeticionDeUsuario(EntradaSalida.leerString("Usuario: "), sistemaDeReserva);

                        password = EntradaSalida.leerString("Clave: ");

                        Administrador admin = new Administrador(usuario, password);

                        sistemaDeReserva.getUsuarios().add(admin);
                        EntradaSalida.mostrarString("\n*Se ha agregado el administrador al sistema*\n");
                        break;
                    } else if (opcion2 == '2') {
                        EntradaSalida.mostrarString("DAR DE BAJA UN ADMINISTRADOR\n");
                        int cantAdmin = 0;

                        EntradaSalida.mostrarString("Los administradores en el sistema con los siguientes:\n");
                        for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
                            if (sistemaDeReserva.getUsuarios().get(i) instanceof Administrador) {
                                EntradaSalida.mostrarString("-> " + sistemaDeReserva.getUsuarios().get(i).toString() + "\n");
                                cantAdmin++;
                            }
                        }

                        if (cantAdmin == 1) {
                            EntradaSalida.mostrarString("Hay un solo administrador en el sistema. No puedes darlo de baja. Crea otro antes de hacer este proceso\n");
                        } else {

                            String usuarioDeAdminAEliminar = EntradaSalida.leerString("Ingrese el nombre del usuario del administrador que desea eliminar: ");

                            int i = 0;
                            boolean encontrado = false;
                            while (i < sistemaDeReserva.getUsuarios().size() && !encontrado) {
                                if (sistemaDeReserva.getUsuarios().get(i) instanceof Administrador) {
                                    if (sistemaDeReserva.getUsuarios().get(i).getUsuario().equals(usuarioDeAdminAEliminar)) {
                                        encontrado = true;
                                        sistemaDeReserva.getUsuarios().remove(i);
                                        EntradaSalida.mostrarString("\n*Se ha dado de baja el administrador correctamente.*\n");
                                    }
                                }
                                i++;
                            }

                            if (i == sistemaDeReserva.getUsuarios().size()) {
                                EntradaSalida.mostrarString("\nNo existe el administrador. Asegurate de haber ingresado bien el usuario.\n");
                            }
                        }
                    }
                    break;
                case '4':
                    EntradaSalida.mostrarString("-- PROCESOS CLIENTES --");
                    char opcion3 = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta un cliente\n"
                            + "[2]Dar de baja un cliente\n"
                            + "[3]Modificar un cliente\n"
                            + "[4]Volver");

                    opcion3 = f.validarNumIngresado('1', '4', opcion3, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta un cliente\n"
                            + "[2]Dar de baja un cliente\n"
                            + "[3]Modificar un cliente\n"
                            + "[4]Volver"));

                    if (opcion3 == '1') {
                        f.darDeAltaCLiente(sistemaDeReserva);
                    } else if (opcion3 == '2') {
                        f.darDeBajaCliente(sistemaDeReserva);
                    } else if (opcion3 == '3') {
                        f.modificarCliente(sistemaDeReserva);
                    }

                    break;
                case '5':
                    EntradaSalida.mostrarString("-- PROCESOS OFICINAS --");
                    char eleccion = EntradaSalida.leerChar("Que proceso desea realizar?\n"
                            + "[1]Dar de alta una oficina\n"
                            + "[2]Dar de baja una oficina\n"
                            + "[3]Modificar una oficina\n"
                            + "[4]Volver");

                    eleccion = f.validarNumIngresado('1', '4', eleccion, ("ERROR. Opcion invalida.\n"
                            + "Que proceso desea realizar?\n"
                            + "[1]Dar de alta una oficina\n"
                            + "[2]Dar de baja una oficina\n"
                            + "[3]Modificar una oficina\n"
                            + "[4]Volver"));

                    if (eleccion == '1') {
                        EntradaSalida.mostrarString("-- DAR DE ALTA UNA OFICINA --");
                        Oficina ofi = new Oficina();
                        int ultimoCodOficina = 0;

                        for (int i = 0; i < sistemaDeReserva.getOficinas().size(); i++) {
                            ultimoCodOficina = Integer.parseInt(sistemaDeReserva.getOficinas().get(i).getCodOficina());

                        }

                        String codOficina = (ultimoCodOficina + 1) + "";
                        ofi.setCodOficina(codOficina);
                        EntradaSalida.mostrarString("Codigo de oficina: " + ofi.getCodOficina());
                        ofi.setDireccion(EntradaSalida.leerString("Ingrese la direccion de la oficina: "));
                        ofi.setProvincia(EntradaSalida.leerString("Ingrese la provincia donde se encuentra la oficina: "));
                        String telefonoOficina = EntradaSalida.leerString("Ingrese el telefono de la oficna: ");
                        telefonoOficina = f.validarTelefono(telefonoOficina, "Ingrese el telefono de la oficna: ");
                        ofi.setTelefono(telefonoOficina);
                        sistemaDeReserva.getOficinas().add(ofi);
                        EntradaSalida.mostrarString("*La reserva ha sido dada de alta con exito*\n");
                    } else if (eleccion == '2') {
                        EntradaSalida.mostrarString("-- DAR DE BAJA UNA OFICINA --");
                        if (!(sistemaDeReserva.getOficinas().size() <= 0)) {
                            for (Oficina o : sistemaDeReserva.getOficinas()) {
                                EntradaSalida.mostrarString(o.toString());
                            }

                            String codOficinaABuscar = EntradaSalida.leerString("\nIngrese el codigo de oficina a dar de baja: ");
                            int i = 0;
                            while (i < sistemaDeReserva.getOficinas().size() && !(codOficinaABuscar.equals(sistemaDeReserva.getOficinas().get(i).getCodOficina()))) {
                                i++;
                            }

                            if (!(i == sistemaDeReserva.getOficinas().size())) {
                                if (EntradaSalida.leerBoolean("Estas seguro que desea dar de baja la reserva de codigo <<" + codOficinaABuscar + ">>  [S/N]")) {
                                    sistemaDeReserva.getOficinas().remove(i);
                                    EntradaSalida.mostrarString("La reserva ha sido dada de baja con exito.");
                                }
                            } else {
                                EntradaSalida.mostrarString("\n\nNo se encuentra la oficina. Asegurate de ingresar bien el codigo de oficina.\n");
                            }
                        } else {
                            EntradaSalida.mostrarString("\nNo hay oficinas en el sistema.");
                        }
                    } else if (eleccion == '3') {
                        EntradaSalida.mostrarString("-- MODIFICAR UNA OFICINA --");
                        for (Oficina o : sistemaDeReserva.getOficinas()) {
                            EntradaSalida.mostrarString(o.toString());
                        }

                        String codigoOficinaAModificar = EntradaSalida.leerString("Ingrese el codigo de oficina a modificar: ");

                        int i = 0;
                        while (i < sistemaDeReserva.getOficinas().size() && !(codigoOficinaAModificar.equals(sistemaDeReserva.getOficinas().get(i).getCodOficina()))) {
                            i++;
                        }

                        if (i == sistemaDeReserva.getOficinas().size()) {
                            EntradaSalida.mostrarString("No existe la oficina. Asegurate de ingresar bien el codigo.");
                        } else {
                            Oficina ofi = new Oficina();
                            ofi = sistemaDeReserva.getOficinas().get(i);
                            char opcionAModificar = EntradaSalida.leerChar("[1]Direccion\n"
                                    + "[2]Provincia\n"
                                    + "[3]Telefono\n"
                                    + "Ingrese lo que quiere modificar: ");

                            switch (opcionAModificar) {
                                case '1':
                                    EntradaSalida.mostrarString("Direccion Actual: " + ofi.getDireccion());
                                    ofi.setDireccion(EntradaSalida.leerString("Direccion nueva: "));
                                    break;
                                case '2':
                                    EntradaSalida.mostrarString("Provincia Actual: " + ofi.getProvincia());
                                    ofi.setProvincia(EntradaSalida.leerString("Provincia nueva: "));
                                    break;
                                case '3':
                                    EntradaSalida.mostrarString("Telefono Actual: " + ofi.getTelefono());
                                    String telefonoOficina = EntradaSalida.leerString("Ingrese el telefono nuevo: ");
                                    telefonoOficina = f.validarTelefono(telefonoOficina, "Ingrese el telefono nuevo: ");
                                    ofi.setTelefono(telefonoOficina);

                                    break;
                                default:
                                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                                    opcionAModificar = '*';
                            }
                        }
                    }

                    break;
                case '6':
                    EntradaSalida.mostrarString("-- LISTADO VENDEDORES ORDENADOS POR FACTURACION --\n");

                    ArrayList<Vendedor> vendedoresEnElSistema = new ArrayList<>();
                    for (int i = 0; i < sistemaDeReserva.getUsuarios().size(); i++) {
                        if (sistemaDeReserva.getUsuarios().get(i) instanceof Vendedor) {
                            vendedoresEnElSistema.add((Vendedor) sistemaDeReserva.getUsuarios().get(i));
                        }
                    }

                    if (vendedoresEnElSistema.size() > 0) {
                        Collections.sort(vendedoresEnElSistema, new CompararVendedores());
                        for (int i = 0; i < vendedoresEnElSistema.size(); i++) {
                            EntradaSalida.mostrarString("->" + vendedoresEnElSistema.get(i).toString() + "\n");
                        }
                    } else {
                        EntradaSalida.mostrarString("*No hay vendedores en el sistema*\n");
                    }

                    break;
                case '7':
                    seguir = true;
                    break;
                case '8':
                    seguir = false;
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            if (opc >= '1' && opc <= '5') {
                try {
                    sistemaDeReserva.serializar("hertz.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } while (opc != '7' && opc != '8');

        return seguir;
    }

}
