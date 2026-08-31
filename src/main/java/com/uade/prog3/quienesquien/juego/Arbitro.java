package com.uade.prog3.quienesquien.juego;

import com.uade.prog3.quienesquien.modelo.Personaje;
import com.uade.prog3.quienesquien.modelo.Pregunta;

import java.util.HashMap;
import java.util.Map;

/**
 * Guarda los personajes secretos y es el unico que los lee. Los jugadores
 * preguntan y solo reciben un si/no, nunca el personaje del rival.
 */
public class Arbitro {

    private final Map<String, Personaje> secretos = new HashMap<>();

    public void registrarSecreto(String jugador, Personaje secreto) {
        secretos.put(jugador, secreto);
    }

    /** Responde una pregunta sobre el secreto del rival. */
    public boolean consultar(String nombreOponente, Pregunta pregunta) {
        return pregunta.cumple(exigir(nombreOponente));
    }

    /** Si la adivinanza coincide con el secreto del rival. */
    public boolean esCorrecta(String nombreOponente, Personaje conjetura) {
        return exigir(nombreOponente).getId() == conjetura.getId();
    }

    private Personaje exigir(String jugador) {
        Personaje secreto = secretos.get(jugador);
        if (secreto == null) {
            throw new IllegalStateException("Sin secreto para " + jugador);
        }
        return secreto;
    }
}
