package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.time.LocalDate;
import java.util.List;

public class FuenteDatosMemoria implements IFuenteDatos {

    @Override
    public IClientes crearClientes() {
        return new IClientes() {
            @Override
            public List<Cliente> get() {
                return List.of();
            }

            @Override
            public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {

            }

            @Override
            public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public Cliente buscar(Cliente cliente) {
                return null;
            }

            @Override
            public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {

            }
        };
    }

    @Override
    public IVehiculos crearVehiculos() {
        return new IVehiculos() {
            @Override
            public List<Vehiculo> get() {
                return List.of();
            }

            @Override
            public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {

            }

            @Override
            public Vehiculo buscar(Vehiculo vehiculo) {
                return null;
            }

            @Override
            public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {

            }
        };
    }

    @Override
    public ITrabajos crearTrabajos() {
        return new ITrabajos() {
            @Override
            public List<Trabajo> get() {
                return List.of();
            }

            @Override
            public List<Trabajo> get(Cliente cliente) {
                return List.of();
            }

            @Override
            public List<Trabajo> get(Vehiculo vehiculo) {
                return List.of();
            }

            @Override
            public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {

            }

            @Override
            public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public Trabajo buscar(Trabajo trabajo) {
                return null;
            }

            @Override
            public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {

            }
        };
    }
}
