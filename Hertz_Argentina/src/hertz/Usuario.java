package hertz;

import java.io.Serializable;

public abstract class Usuario implements Serializable {

    private String usuario;
    private String password;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract boolean proceder(SistemaDeReserva sistemaDeReserva);

    boolean coincideUsrPwd(String datos) {
        return datos.equals(usuario + ":" + password);
    }

}
