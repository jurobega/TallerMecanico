package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.util.HashMap;
import java.util.Map;

public enum Opcion {
    INSERTAR_CLIENTE(10,"Insertar Cliente"),
    BUSCAR_CLIENTE(11,"Buscar Cliente"),
    BORRAR_CLIENTE(12,"Borrar Cliente"),
    LISTAR_CLIENTES(13,"Listar Cliente"),
    MODIFICAR_CLIENTE(14,"Modificar Cliente"),
    INSERTAR_VEHICULO(20,"Insertar Vehiculo"),
    BUSCAR_VEHICULO(21,"Buscar Vehiculo"),
    BORRAR_VEHICULO(22,"Borrar Vehiculo"),
    LISTAR_VEHICULOS(23,"Listar Vehiculo"),
    INSERTAR_REVISION(30,"Insertar Revision"),
    BUSCAR_REVISION(31,"Buscar Revision"),
    BORRAR_REVISION(32,"Borrar Revision"),
    LISTAR_REVISIONES(33,"Listar Revisiones"),
    LISTAR_REVISIONES_CLIENTE(34,"Listar Revisiones Cliente"),
    LISTAR_REVISIONES_VEHICULO(35,"Listar Revisiones Vehiculo"),
    ANADIR_HORAS_REVISION(40,"Añadir Horas Revision"),
    ANADIR_PRECIO_MATERIAL_REVISION(50,"Añadir Precio Material Revision"),
    CERRAR_REVISION(60,"Cerrar Revision"),
    SALIR(0,"Salir");

    private int numeroOpcion;
    private String mensaje;
    private static Map<Integer , Opcion> opciones = new HashMap<>();

    static {
        for (Opcion opcion : values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Opcion(int numeroOpcion , String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion)  {
        return opciones.containsKey(numeroOpcion);
    }

    public static Opcion get( int numeroOpcion) throws TallerMecanicoExcepcion {
        if (!esValida(numeroOpcion)) {
            throw new TallerMecanicoExcepcion("La opcion no es valida.");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("[numeroOpcion=%s, mensaje=%s]", numeroOpcion, mensaje);
    }
}
