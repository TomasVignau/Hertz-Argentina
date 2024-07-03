package hertz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EntradaSalida {

    public static char leerChar(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String st = escaner.nextLine();
        return (st == null || st.length() == 0 ? '0' : st.charAt(0));
    }

    public static String leerString(String texto) {
        Validador f = new Validador();
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String st = escaner.nextLine();
        st = filtrarCadenaHTML(st);
        st = f.verificarString(st, texto);
        return (st == null ? "" : st);
    }

    public static boolean leerBoolean(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String i = escaner.nextLine();
        return i == null || i.charAt(0) == 's' || i.charAt(0) == 'S';
    }

    public static void mostrarString(String s) {
        System.out.println(s);
    }

    public static String leerPassword(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        String password = escaner.nextLine();
        password = filtrarCadenaHTML(password);
        return password;
    }

    public static int leerEntero(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        int numero = escaner.nextInt();
        return numero;
    }

    public static float leerFloat(String texto) {
        System.out.println(texto);
        Scanner escaner = new Scanner(System.in);
        float numero = escaner.nextFloat();
        return numero;
    }

    public static Date leerDate(String texto) {
        Scanner scanner = new Scanner(System.in);

        boolean errorFormatoFecha = true;

        Date fecha = null;

        //Se define el formato de fecha esperado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        do {
            System.out.print(texto);
            String fechaString = scanner.nextLine();

            try {
                // Pasar la cadena ingresada a un objeto Date
                fecha = dateFormat.parse(fechaString);
                errorFormatoFecha = false;
            } catch (ParseException e) {
                // Capturar cualquier excepción de parseo de fecha
                System.out.println("Error: Formato de fecha incorrecto. Asegúrese de ingresar la fecha en formato dd/MM/yyyy.");
                errorFormatoFecha = true;
            }
        } while (errorFormatoFecha);

        return fecha;
    }

    private static String filtrarCadenaHTML(String cadena) {
        boolean estamosEnHTML = false;
        String cadenaAuxiliar = "";
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == '<') {
                estamosEnHTML = true;
            } else if (cadena.charAt(i) == '>') {
                estamosEnHTML = false;
            } else if (!estamosEnHTML) {
                cadenaAuxiliar = cadenaAuxiliar + cadena.charAt(i);
            }
        }
        return cadenaAuxiliar;
    }

    public static String leerUsuario(SistemaDeReserva sistemaDeReserva) {
        Validador f = new Validador();
        String st;
        st = leerString("Usuario");
        st = f.verificarRepeticionDeUsuario(st, sistemaDeReserva);
        return (st == null ? "" : st);
    }
}
