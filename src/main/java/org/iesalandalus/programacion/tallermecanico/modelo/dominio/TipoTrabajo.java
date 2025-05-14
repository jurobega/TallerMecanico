package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public enum TipoTrabajo {
    MECANICO("Mecánico"),
    REVISION("Revision");

    private String nombre;

    private TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        TipoTrabajo tipo = null;
        if (trabajo instanceof Mecanico){
            tipo = MECANICO;
        } else if (trabajo instanceof Revision) {
            tipo = REVISION;
        }
        return tipo;
    }
}
