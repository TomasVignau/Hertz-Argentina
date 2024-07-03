package hertz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Reserva implements Serializable {

    private String codDeReserva;
    private Cliente cliente;
    private ArrayList<Vehiculo> vehiculos;
    private Date fechaInicio;
    private Date fechaFinal;
    private float precioAlquilerVehiculo;
    private float precioTotal;
    private Oficina oficinaDeRetiro;
    private String medioPorDondeSeRealizaLaReserva;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public float getPrecioAlquilerVehiculo() {
        return precioAlquilerVehiculo;
    }

    public void setPrecioAlquilerVehiculo(float precioAlquilerVehiculo) {
        this.precioAlquilerVehiculo = precioAlquilerVehiculo;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------------------\n"
                + "Codigo de Reserva: " + this.getCodDeReserva()
                + "\nFecha de Inicio: " + this.getFechaInicio().toString()
                + "\nFecha de Final: " + this.getFechaFinal().toString()
                + "\nPrecio Total: " + this.getPrecioTotal()
                + "\nAutos: " + this.getVehiculos().toString()
                + "\nOficina: " + this.getOficinaDeRetiro().toString()
                + "\n------------------------------------------------------------\n";
    }

    public ArrayList<Vehiculo> getVehiculos() {
        if (vehiculos == null) {
            vehiculos = new ArrayList<>();
        }
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public String getCodDeReserva() {
        return codDeReserva;
    }

    public void setCodDeReserva(String codDeReserva) {
        this.codDeReserva = codDeReserva;
    }

    public Oficina getOficinaDeRetiro() {
        return oficinaDeRetiro;
    }

    public void setOficinaDeRetiro(Oficina oficinaDeRetiro) {
        this.oficinaDeRetiro = oficinaDeRetiro;
    }

    public String getMedioPorDondeSeRealizaLaReserva() {
        return medioPorDondeSeRealizaLaReserva;
    }

    public void setMedioPorDondeSeRealizaLaReserva(String medioPorDondeSeRealizaLaReserva) {
        this.medioPorDondeSeRealizaLaReserva = medioPorDondeSeRealizaLaReserva;
    }
}
