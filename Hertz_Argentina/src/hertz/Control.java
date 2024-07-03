package hertz;

import java.io.IOException;

public class Control {

    public void ejecutar() {
        SistemaDeReserva sistemaDeReserva = new SistemaDeReserva();

        boolean seguir;
        try {
            sistemaDeReserva = sistemaDeReserva.deSerializar("hertz.txt");
            seguir = EntradaSalida.leerBoolean("\t-- HERTZ ARGENTINA --\n[Empresa dedicada al alquiler de autos]\nDesea ingresar?[S/N]");
        } catch (Exception e) {
            String usuario = EntradaSalida.leerString("-- HERTZ ARGENTINA --\n[Empresa dedicada al alquiler de autos]\nArranque inicial del sistema.\n"
                    + "Sr(a) Administrador(a), ingrese su nombre de usuario:");
            if (usuario.equals("")) {
                throw new NullPointerException("ERROR: El usuario no puede ser nulo.");
            }
            String password = EntradaSalida.leerPassword("Ingrese su password:");
            if (password.equals("")) {
                throw new NullPointerException("ERROR: La password no puede ser nula.");
            }
            sistemaDeReserva.getUsuarios().add(new Administrador(usuario, password));
            try {
                sistemaDeReserva.serializar("hertz.txt");
                EntradaSalida.mostrarString("El arranque ha sido exitoso. Ahora se debe reiniciar el sistema...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            seguir = false;
        }

        int controlador = 0;

        while (seguir) {

            if (controlador != 0) {
                EntradaSalida.mostrarString("\t-- HERTZ ARGENTINA --\n[Empresa dedicada al alquiler de autos]\n");
            }
            String usuario = EntradaSalida.leerString("Ingrese el usuario:");
            String password = EntradaSalida.leerPassword("Ingrese la password:");

            Usuario u = sistemaDeReserva.buscarUsuario(usuario + ":" + password);

            if (u == null) {
                EntradaSalida.mostrarString("ERROR: La combinacion usuario/password ingresada no es valida.");
                controlador = 0;
            } else {
                seguir = u.proceder(sistemaDeReserva);  // POLIMORFISMO
                controlador = controlador + 1;
            }

        }
    }
}
