    package org.iesalandalus.programacion.tallermecanico;

    import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
    import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
    import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
    import org.iesalandalus.programacion.tallermecanico.vista.Vista;

    public class Main {
        public static void main(String[] args) throws TallerMecanicoExcepcion {
            Modelo modelo = new ModeloCascada();
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo , vista);
            controlador.comenzar();
        }
    }
