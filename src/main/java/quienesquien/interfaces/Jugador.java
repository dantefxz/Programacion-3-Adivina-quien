package quienesquien.interfaces;

import quienesquien.modelo.Pregunta;

/** Interfaz comun a humano y maquina, para que el motor los trate igual. */
public interface Jugador {

    String getNombre();

    Accion decidir();

    void recibirRespuestaPregunta(Pregunta pregunta, boolean respuesta);

    void recibirResultadoAdivinanza(boolean acerto);
}
