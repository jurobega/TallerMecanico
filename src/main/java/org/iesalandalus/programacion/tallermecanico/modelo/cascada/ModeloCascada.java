package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {
    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos,"La fabrica de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        System.out.println("Modelo comenzado.");
    }

    @Override
    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"No se puede añadir un cliente nulo.");
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehiculo nulo.");
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"No se puede insertar una revision nula.");
        Cliente clienteExistente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculoExistente = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajo = new Revision(clienteExistente, vehiculoExistente, trabajo.getFechaInicio());
        } else if (trabajo instanceof Mecanico) {
            trabajo = new Mecanico(clienteExistente, vehiculoExistente, trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }


    @Override
    public Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente),"No puedes buscar un cliente nulo.");
        return new Cliente(cliente);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo),"No puedes buscar un vehiculo nulo.");
        return vehiculo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No puedes buscar una revisión nula.");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion{
        return clientes.modificar(cliente,nombre,telefono);
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion{
        return trabajos.anadirHoras(trabajo,horas);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion{
        return trabajos.anadirPrecioMaterial(trabajo,precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion{
        return trabajos.cerrar(trabajo,fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion{
        List<Trabajo> revisionesClientes = trabajos.get(cliente);
        for (Trabajo trabajo : revisionesClientes){
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        List<Trabajo> revisionesVehiculos = trabajos.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculos) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion{
        trabajos.borrar(trabajo);
    }


    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copiaCliente = new ArrayList<>();
        for (Cliente cliente : clientes.get()){
            copiaCliente.add(new Cliente(cliente));
        }
        return copiaCliente;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copiaRevision = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            if (trabajo instanceof Mecanico mecanico) {
                copiaRevision.add(new Mecanico(mecanico));
            } else if (trabajo instanceof Revision revision) {
                copiaRevision.add(new Revision(revision));
            }
        }
        return copiaRevision;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente){
        List<Trabajo> copiaRevision = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)){
            if (trabajo instanceof Mecanico mecanico) {
                copiaRevision.add(new Mecanico(mecanico));
            } else if (trabajo instanceof Revision revision) {
                copiaRevision.add(new Revision(revision));
            }
        }
        return copiaRevision;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> copiaRevision = new ArrayList<>();
        for ( Trabajo trabajo : trabajos.get(vehiculo)){
            if (trabajo instanceof Mecanico mecanico) {
                copiaRevision.add(new Mecanico(mecanico));
            } else if (trabajo instanceof Revision revision) {
                copiaRevision.add(new Revision(revision));
            }
        }
        return copiaRevision;
    }
    
}
