package com.uade.prog3.quienesquien.juego;

import com.uade.prog3.quienesquien.modelo.Personaje;
import com.uade.prog3.quienesquien.modelo.Pregunta;

/** Lo que un jugador hace en su turno: preguntar o adivinar. */
public sealed interface Accion permits Accion.Preguntar, Accion.Adivinar {

    record Preguntar(Pregunta pregunta) implements Accion {
    }

    record Adivinar(Personaje personaje) implements Accion {
    }
}
