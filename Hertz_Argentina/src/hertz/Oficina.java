package hertz;

import java.io.Serializable;

public class Oficina implements Serializable {

    private String codOficina;
    private String provincia;
    private String direccion;
    private String telefono;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codReserva) {
        this.codOficina = codReserva;
    }

    public String toString() {
        return "Codigo de Oficina: " + this.getCodOficina()
                + "\nDireccion: " + this.getDireccion() + ", " + this.getProvincia()
                + "\nTelefono: " + this.getTelefono();
    }
}
