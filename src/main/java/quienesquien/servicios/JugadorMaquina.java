package quienesquien.servicios;

import quienesquien.interfaces.Accion;
import quienesquien.interfaces.EstrategiaMaquina;
import quienesquien.interfaces.Registro;
import quienesquien.modelo.Personaje;
import quienesquien.modelo.Pregunta;

import java.util.List;

public class JugadorMaquina extends JugadorBase {

    private final EstrategiaMaquina estrategia;

    public JugadorMaquina(String nombre, List<Personaje> rosterOrdenado, Registro log) {
        super(nombre, rosterOrdenado, log);
        this.estrategia = new EstrategiaGreedy(rastreador, log);
    }

    @Override
    public Accion decidir() {
        log.traza("%s: %d candidatos posibles.", nombre, rastreador.cantidad());

        if (rastreador.cantidad() <= 1) {
            Personaje conjetura = rastreador.cantidad() == 1 ? rastreador.unico() : roster.get(0);
            return adivinar(conjetura);
        }

        Pregunta pregunta = estrategia.mejorPregunta();
        if (pregunta == null) {
            return adivinar(rastreador.ordenadosParaMostrar().get(0));
        }
        return new Accion.Preguntar(pregunta);
    }

    private Accion adivinar(Personaje personaje) {
        ultimaConjetura = personaje;
        return new Accion.Adivinar(personaje);
    }
}
