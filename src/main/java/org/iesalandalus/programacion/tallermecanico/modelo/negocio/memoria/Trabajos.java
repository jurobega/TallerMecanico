package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {
    private final List<Trabajo> coleccionRevisiones;

    public Trabajos() {
        coleccionRevisiones = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    @Override
    public List<Trabajo> get(Cliente cliente){
        List<Trabajo> revisionesClientes = new ArrayList<>();
        for (Trabajo trabajo : coleccionRevisiones) {
            if (trabajo.getCliente().equals(cliente)){
                revisionesClientes.add(trabajo);
            }
        }
        return revisionesClientes;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo){
        List<Trabajo> revisionVehiculos = new ArrayList<>();
        for (Trabajo trabajo : coleccionRevisiones ){
            if (trabajo.getVehiculo().equals(vehiculo)){
                revisionVehiculos.add(trabajo);
            }
        }
        return revisionVehiculos;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente() , trabajo.getVehiculo() , trabajo.getFechaInicio());
        coleccionRevisiones.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente , Vehiculo vehiculo , LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : coleccionRevisiones) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)){
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                } else if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            } else {
                if (trabajo.getCliente().equals(cliente) && !fechaInicio.isAfter(trabajo.getFechaFin())){
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                } else if (trabajo.getVehiculo().equals(vehiculo) && !fechaInicio.isAfter(trabajo.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"No puedo añadir horas a un trabajo nulo.");
        if (horas <= 0){
            throw new IllegalArgumentException("Las horas no pueden ser menor de cero.");
        }
        Trabajo trabajoExistente = getTrabajoAbierto(trabajo);
        trabajoExistente.anadirHoras(horas);
        return trabajoExistente;
    }


    private Trabajo getTrabajoAbierto(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"La revision no puede ser nula.");
        int indice = coleccionRevisiones.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return coleccionRevisiones.get(indice);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir precio del material a un trabajo nulo.");
        trabajo = getTrabajoAbierto(trabajo);
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material no puede ser menor que cero.");
        }
        if (trabajo instanceof Revision) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        } else if (trabajo instanceof Mecanico mecanico){
            mecanico.anadirPrecioMaterial(precioMaterial);
        }
        return trabajo;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"No puedo cerrar un trabajo nulo.");
        Objects.requireNonNull(fechaFin,"La fecha no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de fin no puede ser posterior a la de hoy.");
        }
        Trabajo trabajoExsistente = getTrabajoAbierto(trabajo);
        if (fechaFin.isBefore(trabajoExsistente.getFechaInicio())){
            throw new TallerMecanicoExcepcion("La fecha de fin no puede ser anterior a la de inicio.");
        }
        trabajoExsistente.cerrar(fechaFin);
        return trabajoExsistente;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"No se puede buscar un trabajo nulo.");
        int indice = coleccionRevisiones.indexOf(trabajo);
        return (indice == -1) ? null : coleccionRevisiones.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No se puede borrar un trabajo nulo.");
        int indice = coleccionRevisiones.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
        coleccionRevisiones.remove(indice);
    }
}
