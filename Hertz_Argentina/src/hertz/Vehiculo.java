package hertz;

import java.io.Serializable;

public class Vehiculo implements Serializable {

    private String patente;
    private String modelo;
    private String color;
    private String marca;
    private boolean entregado;
    private float precio;
    private Oficina oficinaInicial;
    private float litrosDeGasolinaAlIniciarLaReserva;
    private float litrosDeGasolinaAlFinalizarLaReserva;

    @Override
    public String toString() {
        return "Marca: " + this.getMarca() + " Modelo: " + this.getModelo() + " Patente: " + this.getPatente()
                + " Color: " + this.getColor() + " Precio: " + this.getPrecio() + " || Oficina Inicial: " + this.getOficinaInicial().getCodOficina() + " Direccion: " + this.getOficinaInicial().getDireccion();
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Oficina getOficinaInicial() {
        return oficinaInicial;
    }

    public void setOficinaInicial(Oficina oficinaInicial) {
        this.oficinaInicial = oficinaInicial;
    }

    public float getLitrosDeGasolinaAlIniciarLaReserva() {
        return litrosDeGasolinaAlIniciarLaReserva;
    }

    public void setLitrosDeGasolinaAlIniciarLaReserva(float litrosDeGasolinaAlIniciarLaReserva) {
        this.litrosDeGasolinaAlIniciarLaReserva = litrosDeGasolinaAlIniciarLaReserva;
    }

    public float getLitrosDeGasolinaAlFinalizarLaReserva() {
        return litrosDeGasolinaAlFinalizarLaReserva;
    }

    public void setLitrosDeGasolinaAlFinalizarLaReserva(float litrosDeGasolinaAlFinalizarLaReserva) {
        this.litrosDeGasolinaAlFinalizarLaReserva = litrosDeGasolinaAlFinalizarLaReserva;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

}
