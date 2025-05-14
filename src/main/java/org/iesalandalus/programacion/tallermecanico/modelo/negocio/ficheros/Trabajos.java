package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {
    private static String FICHEROS_TRABAJO = "";
    private static DateTimeFormatter FORMATO_FECHA;
    private static String RAIZ = "";
    private static String TRABAJO = "";
    private static String CLIENTE = "";
    private static String VEHICULO = "";
    private static String FECHA_INICIO = "";
    private static String FECHA_FIN = "";
    private static String HORAS = "";
    private static String PRECIO_MATERIAL = "";
    private static String TIPO = "";
    private static String REVISION = "";
    private static String MECANICO = "";

    private final List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente){
        List<Trabajo> revisionesClientes = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)){
                revisionesClientes.add(trabajo);
            }
        }
        return revisionesClientes;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo){
        List<Trabajo> revisionVehiculos = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos){
            if (trabajo.getVehiculo().equals(vehiculo)){
                revisionVehiculos.add(trabajo);
            }
        }
        return revisionVehiculos;
    }

    @Override
    public Map<TipoTrabajo , Integer > getEstadisticasMensuales(LocalDate mes) {
        Map<TipoTrabajo , Integer> estadisticas = inicializarEstadisticas();
        for (Trabajo trabajo : coleccionTrabajos) {
            LocalDate fecha = trabajo.getFechaInicio();
            if (fecha.getMonthValue() == mes.getMonthValue() && fecha.getYear() == mes.getYear() ){
                TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
                estadisticas.put(tipoTrabajo , estadisticas.get(tipoTrabajo) + 1);
            }
        }
        return estadisticas;
    }

    private Map<TipoTrabajo , Integer> inicializarEstadisticas() {
        Map<TipoTrabajo , Integer> estadisticas = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()){
            estadisticas.put(tipoTrabajo , 0);
        }
        return estadisticas;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente() , trabajo.getVehiculo() , trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente , Vehiculo vehiculo , LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : coleccionTrabajos) {
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
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.anadirHoras(horas);
        return trabajoEncontrado;
    }


    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajoEncontrado = null;
        Iterator<Trabajo> iteradorTrabajos = coleccionTrabajos.iterator();
        while (iteradorTrabajos.hasNext() && trabajoEncontrado == null ) {
            Trabajo trabajo = iteradorTrabajos.next();
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrado() ) {
                trabajoEncontrado = trabajo;
            }
        }
        if (trabajoEncontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Revision) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        } else if (trabajoEncontrado instanceof Mecanico mecanico){
            mecanico.anadirPrecioMaterial(precioMaterial);
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"No puedo cerrar un trabajo nulo.");
        Objects.requireNonNull(fechaFin,"La fecha no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de fin no puede ser posterior a la de hoy.");
        }
        Trabajo trabajoExsistente = getTrabajoAbierto(trabajo.getVehiculo());
        if (fechaFin.isBefore(trabajoExsistente.getFechaInicio())){
            throw new TallerMecanicoExcepcion("La fecha de fin no puede ser anterior a la de inicio.");
        }
        trabajoExsistente.cerrar(fechaFin);
        return trabajoExsistente;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No se puede borrar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
        coleccionTrabajos.remove(indice);
    }
}
