package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cliente {

    private static final String ER_NOMBRE = "^[A-ZÁÉÍÓÚñ][a-záéíóúñ]+( [A-ZÁÉÍÓÚñ][a-záéíóúñ]+)*$";
    private static final String ER_DNI = "\\d{8}[A-Z]";
    private static final String ER_TELEFONO = "\\d{9}";

    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre , String dni , String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        Objects.requireNonNull(cliente,"No es posible copiar un cliente nulo.");
        this.nombre = cliente.nombre;
        this.dni = cliente.dni;
        this.telefono = cliente.telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
        if (!Pattern.matches(ER_NOMBRE , nombre)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni,"El DNI no puede ser nulo.");
        if (!Pattern.matches(ER_DNI, dni)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        if (comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }
        this.dni = dni;
    }

    private boolean comprobarLetraDni(String dni) {
        boolean comprobarLetra = false;
        String letraP = "TRWAGMYFPDXBNJZSQVHLCKE";
        String letraDni = dni.substring(8,9);
        int numeroDni = Integer.parseInt(dni.substring(0,8));
        String letraNecesaria = String.valueOf(letraP.charAt(numeroDni % 23));
        if (letraDni.equals(letraNecesaria)){
            comprobarLetra = true;
        }
        return !comprobarLetra;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono,"El teléfono no puede ser nulo.");
        if (!Pattern.matches(ER_TELEFONO, telefono)){
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    public static Cliente get(String dni) {
        Objects.requireNonNull(dni,"El DNI no puede ser nulo.");
        if (!Pattern.matches(ER_DNI , dni)){
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        if (new Cliente("Nombre", dni, "000000000").comprobarLetraDni(dni)){
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }
        return new Cliente("Temp",dni,"000000000");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)",nombre, dni, telefono);
    }
}
