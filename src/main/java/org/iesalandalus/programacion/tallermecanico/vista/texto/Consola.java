package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {

    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {}

    static void mostrarCabecera(String mensaje) {
        System.out.printf("%n%s%n" , mensaje);
        System.out.printf("-".repeat(mensaje.length()).concat("%n%n"));
    }

    static void mostrarMenu(){
        String cabecera = "Gestión Del Taller Mecánico";
        mostrarCabecera(cabecera);
        for (Evento opcion : Evento.values()) {
            System.out.println(opcion);
        }
    }

    static Evento elegirOpcion() {
        Evento opcion = null;
        do {
             try {
                  opcion = Evento.get(leerEntero("\nElige una opción: "));
             } catch (TallerMecanicoExcepcion e) {
                 System.out.printf("ERROR: %s%n", e.getMessage());
             }
        } while (opcion == null);
        return opcion;
    }

    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    static LocalDate leerFecha(String mensaje) {
            LocalDate fecha;
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
            mensaje = String.format("%s (%s): ", mensaje,CADENA_FORMATO_FECHA);
            try {
                fecha = LocalDate.parse(leerCadena(mensaje) , formatoFecha);
            }catch (DateTimeParseException e) {
                fecha = null;
            }
            return fecha;
    }

}
