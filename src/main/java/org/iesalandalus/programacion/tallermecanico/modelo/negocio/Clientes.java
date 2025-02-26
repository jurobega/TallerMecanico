package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes {
    private final List<Cliente> coleccionCliente;

    public Clientes() {
        coleccionCliente = new ArrayList<>();
    }

    public List<Cliente> get() {
        return new ArrayList<>(coleccionCliente);
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"No se puede insertar un cliente nulo.");
        if (coleccionCliente.contains(cliente)){
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        coleccionCliente.add(cliente);
    }

    public Cliente modificar(Cliente cliente ,String nombre , String telefono) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"No se puede modificar un cliente nulo.");
        Cliente clienteexistente = buscar(cliente);
        if (clienteexistente == null){
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()) {
            clienteexistente.setNombre(nombre);
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteexistente.setTelefono(telefono);
        }
        return clienteexistente;
    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente,"No se puede buscar un cliente nulo.");
        int indice = coleccionCliente.indexOf(cliente);
        return (indice == (-1)) ? null : coleccionCliente.get(indice);
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"No se puede borrar un cliente nulo.");
        int incice = coleccionCliente.indexOf(cliente);
        if (incice == (-1)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
        coleccionCliente.remove(incice);
    }

}
