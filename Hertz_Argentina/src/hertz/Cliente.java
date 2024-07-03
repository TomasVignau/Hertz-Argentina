package hertz;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {

    private String dni;
    private String nombre;
    private String direccion;
    private String telefono;
    private String codCliente;

    @Override
    public boolean proceder(SistemaDeReserva sistemaDeReserva) {
        EntradaSalida.mostrarString("Bienvenido a HERTZ [Reserva de Autos]\n");
        String codClienteAConsultar = this.getCodCliente();

        int contadorReservas = 0;
        for (int i = 0; i < sistemaDeReserva.getReservas().size(); i++) {
            if (codClienteAConsultar.equals(sistemaDeReserva.getReservas().get(i).getCliente().getCodCliente())) {
                EntradaSalida.mostrarString("RESERVA N-" + i + 1 + "\n");
                EntradaSalida.mostrarString(sistemaDeReserva.getReservas().get(i).toString() + "\n");
                contadorReservas++;
            }
        }
        if (contadorReservas != 0) {
            EntradaSalida.mostrarString("\nRECORDA DEVOLVER EL VEHICULO EN TIEMPO Y FORMA" + "\nMUCHAS GRACIAS POR REALIZAR SU CONSULTA!\n");
        } else {
            EntradaSalida.mostrarString("NO TIENES RESERVAS PENDIENTES" + "\nMUCHAS GRACIAS POR REALIZAR SU CONSULTA!\n");
        }
        return true;
    }

    @Override
    public String toString() {
        return "CodCliente: " + this.getCodCliente() + " DNI: " + this.getDni() + " Nombre: " + this.getNombre()
                + " Telefono: " + this.getTelefono() + " Direccion: " + this.getDireccion();
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
