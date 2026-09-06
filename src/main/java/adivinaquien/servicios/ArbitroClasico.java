package adivinaquien.servicios;

import adivinaquien.interfaces.Arbitro;
import adivinaquien.modelo.Personaje;
import adivinaquien.modelo.Pregunta;

import java.util.HashMap;
import java.util.Map;

public class ArbitroClasico implements Arbitro {

    private final Map<String, Personaje> secretos = new HashMap<>();

    @Override
    public void registrarSecreto(String jugador, Personaje secreto) {
        secretos.put(jugador, secreto);
    }

    @Override
    public boolean consultar(String nombreOponente, Pregunta pregunta) {
        return pregunta.cumple(exigir(nombreOponente));
    }

    @Override
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
