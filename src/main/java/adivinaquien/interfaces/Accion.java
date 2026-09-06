package adivinaquien.interfaces;

import adivinaquien.modelo.Personaje;
import adivinaquien.modelo.Pregunta;

/** Lo que un jugador hace en su turno: preguntar o adivinar. */
public sealed interface Accion permits Accion.Preguntar, Accion.Adivinar {

    record Preguntar(Pregunta pregunta) implements Accion {
    }

    record Adivinar(Personaje personaje) implements Accion {
    }
}
