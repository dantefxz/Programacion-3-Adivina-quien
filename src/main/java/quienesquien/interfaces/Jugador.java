package com.uade.prog3.quienesquien.juego;

import com.uade.prog3.quienesquien.modelo.Pregunta;

/** Interfaz comun a humano y maquina, para que el motor los trate igual. */
public interface Jugador {

    String getNombre();

    Accion decidir();

    void recibirRespuestaPregunta(Pregunta pregunta, boolean respuesta);

    void recibirResultadoAdivinanza(boolean acerto);
}
