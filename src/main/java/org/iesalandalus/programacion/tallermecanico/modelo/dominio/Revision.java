package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 35;

    public Revision(Cliente cliente , Vehiculo vehiculo , LocalDate fechaInicio){
        super(cliente , vehiculo , fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    public float getPrecioEspecifico() {
        return getHoras() * FACTOR_HORA;
    }

    @Override
    public String toString() {
        String muestra;
        String precioTotal = String.format("%.2f" , getPrecio());
        if (!estaCerrado()) {
            muestra = String.format("Revisión -> %s - %s (%s) - %s %s - %s (%s - ): %d horas", cliente.getNombre() , cliente.getDni() , cliente.getTelefono() , getVehiculo().marca() ,getVehiculo().modelo() , getVehiculo().matricula(), fechaInicio.format(FORMATO_FECHA) , horas);
        } else {
            muestra = String.format("Revisión -> %s - %s (%s) - %s %s - %s (%s - %s): %d horas, %s € total", cliente.getNombre() , cliente.getDni() , cliente.getTelefono() , getVehiculo().marca() ,getVehiculo().modelo() , getVehiculo().matricula(), fechaInicio.format(FORMATO_FECHA),fechaFin.format(FORMATO_FECHA) , horas , precioTotal);
        }
        return muestra;
    }
}
