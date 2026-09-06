package quienesquien.interfaces;

import quienesquien.modelo.Personaje;
import quienesquien.modelo.Pregunta;

/** Lo que un jugador hace en su turno: preguntar o adivinar. */
public sealed interface Accion permits Accion.Preguntar, Accion.Adivinar {

    record Preguntar(Pregunta pregunta) implements Accion {
    }

    record Adivinar(Personaje personaje) implements Accion {
    }
}
