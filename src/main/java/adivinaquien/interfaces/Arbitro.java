package adivinaquien.interfaces;

import adivinaquien.modelo.Personaje;
import adivinaquien.modelo.Pregunta;

public interface Arbitro {

    void registrarSecreto(String jugador, Personaje secreto);

    boolean consultar(String nombreOponente, Pregunta pregunta);

    boolean esCorrecta(String nombreOponente, Personaje conjetura);
}
