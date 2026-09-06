package adivinaquien.interfaces;

import adivinaquien.modelo.Personaje;

import java.util.List;

/** Salida de texto del juego. En modo verboso agrega la traza interna de la maquina. */
public interface Registro {

    void titulo(String formato, Object... args);

    void info(String formato, Object... args);

    /** Detalle interno; solo sale en modo verboso. */
    void traza(String formato, Object... args);

    void tabla(List<Personaje> personajes);

    /** Igual que tabla pero solo en modo verboso. */
    void tablaTraza(List<Personaje> personajes);

    boolean esVerboso();
}
