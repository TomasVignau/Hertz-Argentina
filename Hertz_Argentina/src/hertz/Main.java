package hertz;

public class Main {

    public static void main(String[] args) {
        Control control = new Control();
        try {
            control.ejecutar();
        } catch (NullPointerException e) {
            EntradaSalida.mostrarString(e.getMessage());
        }
    }
}
