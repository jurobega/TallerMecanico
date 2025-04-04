package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private static Map<Evento , List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos( Evento... eventos ) {
        Objects.requireNonNull(eventos,"Se debe gestionar algún evento");
        for (Evento evento : eventos ) {
            receptores.put(evento , new ArrayList<>());
        }
    }

    public void suscribir( ReceptorEventos receptor , Evento... evento ){
        Objects.requireNonNull(receptor,"El receptor de eventos no puede ser nulo.");
        Objects.requireNonNull(evento,"Te debes suscribir a algún evento.");
        for (Evento eventos : evento ) {
            List<ReceptorEventos> suscriptores = receptores.get(eventos);
            suscriptores.add(receptor);
        }
    }

    public void desuscribir( ReceptorEventos receptor , Evento... eventos ){
        Objects.requireNonNull(receptor,"El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos,"Te debes desuscribir de algún evento");
        for (Evento evento : eventos ) {
            List<ReceptorEventos> suscriptores = receptores.get(evento);
            suscriptores.add(receptor);
        }
    }

    public void notificar( Evento evento ) {
        Objects.requireNonNull(evento,"No se puede modificar un evento nulo.");
        for (ReceptorEventos receptorEventos : receptores.get(evento)){
            receptorEventos.actualizar(evento);
        }
    }
}
