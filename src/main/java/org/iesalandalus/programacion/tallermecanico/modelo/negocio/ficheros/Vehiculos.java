package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {
    private final List<Vehiculo> coleccionVehiculo;

    public Vehiculos() {
        coleccionVehiculo = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get() {
        return new ArrayList<>(coleccionVehiculo);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehículo nulo.");
        if (coleccionVehiculo.contains(vehiculo)){
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
        coleccionVehiculo.add(vehiculo);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculo.indexOf(vehiculo);
        return (indice == -1) ? null : coleccionVehiculo.get(indice);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        int indice = coleccionVehiculo.indexOf(vehiculo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
        coleccionVehiculo.remove(indice);
    }
}
