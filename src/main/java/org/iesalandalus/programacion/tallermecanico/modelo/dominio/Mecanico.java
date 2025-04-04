package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;

public class Mecanico extends Trabajo {

    private static final float FACTOR_HORA = 30;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5F;

    private float precioMaterial;

    public Mecanico(Cliente cliente , Vehiculo vehiculo , LocalDate fechaInicio) {
        super(cliente , vehiculo , fechaInicio);
    }

    public Mecanico(Mecanico mecanico ) {
        super(mecanico);
        this.precioMaterial = mecanico.getPrecioMaterial();
    }
    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        this.precioMaterial +=  precioMaterial ;
    }

    public float getPrecioEspecifico() {
        return (getHoras() * FACTOR_HORA) + (precioMaterial * FACTOR_PRECIO_MATERIAL);
    }

    @Override
    public String toString() {
        String precioMaterial = String.format("%.2f" , getPrecioMaterial());
        String precioTotal = String.format("%.2f" , getPrecio());
        String muestra;
        if (!estaCerrado()){
            muestra = String.format("Mecánico -> %s - %s (%s) - %s %s - %s (%s - ): %d horas, %s € en material" ,cliente.getNombre() , cliente.getDni() , cliente.getTelefono()  , vehiculo.marca() , vehiculo.modelo() , vehiculo.matricula() , fechaInicio.format(FORMATO_FECHA)  , getHoras() , precioMaterial );
        } else {
            muestra = String.format("Mecánico -> %s - %s (%s) - %s %s - %s (%s - %s): %d horas, %s € en material, %s € total" ,cliente.getNombre() , cliente.getDni() , cliente.getTelefono()  , vehiculo.marca() , vehiculo.modelo() , vehiculo.matricula() , fechaInicio.format(FORMATO_FECHA),fechaFin.format(FORMATO_FECHA)  , getHoras() , precioMaterial, precioTotal );
        }
        return muestra;
    }
}
