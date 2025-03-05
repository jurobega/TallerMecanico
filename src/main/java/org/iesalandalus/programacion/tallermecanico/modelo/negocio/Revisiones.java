package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {
    private final List<Revision> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Revision> get(Cliente cliente){
        List<Revision> revisionesClientes = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)){
                revisionesClientes.add(revision);
            }
        }
        return revisionesClientes;
    }

    public List<Revision> get(Vehiculo vehiculo){
        List<Revision> revisionVehiculos = new ArrayList<>();
        for (Revision revision : coleccionRevisiones ){
            if (revision.getVehiculo().equals(vehiculo)){
                revisionVehiculos.add(revision);
            }
        }
        return revisionVehiculos;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente() , revision.getVehiculo() , revision.getFechaInicio());
        coleccionRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente , Vehiculo vehiculo , LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        for (Revision revision : coleccionRevisiones) {
            if (!revision.estaCerrada()) {
                if (revision.getCliente().equals(cliente)){
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                } else if (revision.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                }
            } else {
                if (revision.getCliente().equals(cliente) && !fechaRevision.isAfter(revision.getFechaFin())){
                    throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                } else if (revision.getVehiculo().equals(vehiculo) && !fechaRevision.isAfter(revision.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }

    public Revision anadirHoras(Revision revision , int horas) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(revision,"No puedo operar sobre una revisión nula.");
        if (horas <= 0){
            throw new IllegalArgumentException("Las horas no pueden ser menor de cero.");
        }
        Revision revisionExistente = getRevision(revision);
        revisionExistente.anadirHoras(horas);
        return revisionExistente;
    }


    private Revision getRevision(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"La revision no puede ser nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        return coleccionRevisiones.get(indice);
    }

    public Revision anadirPrecioMaterial(Revision revision , float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"No puedo operar sobre una revisión nula.");
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material no puede ser menor que cero.");
        }
        Revision revisionExistente = getRevision(revision);
        revisionExistente.anadirPrecioMaterial(precioMaterial);
        return revisionExistente;
    }

    public Revision cerrar(Revision revision , LocalDate fechaFin) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(revision,"No puedo operar sobre una revisión nula.");
        Objects.requireNonNull(fechaFin,"La fecha no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de fin no puede ser posterior a la de hoy.");
        }
        Revision revisionExsistente = getRevision(revision);
        if (fechaFin.isBefore(revisionExsistente.getFechaInicio())){
            throw new TallerMecanicoExcepcion("La fecha de fin no puede ser anterior a la de inicio.");
        }
        revisionExsistente.cerrar(fechaFin);
        return revisionExsistente;
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision,"No se puede buscar una revisión nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        return (indice == -1) ? null : coleccionRevisiones.get(indice);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"No se puede borrar una revisión nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        coleccionRevisiones.remove(indice);
    }
}
