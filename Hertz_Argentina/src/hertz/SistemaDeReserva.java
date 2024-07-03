package hertz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SistemaDeReserva implements Serializable {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Oficina> oficinas;
    private ArrayList<Reserva> reservas;

    public SistemaDeReserva() {
        usuarios = new ArrayList<Usuario>();
        vehiculos = new ArrayList<Vehiculo>();
        oficinas = new ArrayList<Oficina>();
        reservas = new ArrayList<Reserva>();
    }

    public SistemaDeReserva deSerializar(String a) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(a);
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaDeReserva s = (SistemaDeReserva) o.readObject();
        o.close();
        f.close();
        return s;
    }

    public void serializar(String a) throws IOException {
        FileOutputStream f = new FileOutputStream(a);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    public Usuario buscarUsuario(String datos) {
        int i = 0;
        boolean encontrado = false;
        Usuario user = null;
        for (Usuario u : usuarios) {
            if (u.coincideUsrPwd(datos)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public ArrayList<Oficina> getOficinas() {
        return oficinas;
    }

    public void setOficinas(ArrayList<Oficina> oficinas) {
        this.oficinas = oficinas;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
}
