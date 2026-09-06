package quienesquien.interfaces;

import quienesquien.modelo.Personaje;
import quienesquien.modelo.Pregunta;

public interface Arbitro {

    void registrarSecreto(String jugador, Personaje secreto);

    boolean consultar(String nombreOponente, Pregunta pregunta);

    boolean esCorrecta(String nombreOponente, Personaje conjetura);
}
