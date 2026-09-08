package adivinaquien.servicios;

import adivinaquien.interfaces.Accion;
import adivinaquien.interfaces.EstrategiaMaquina;
import adivinaquien.interfaces.Registro;
import adivinaquien.modelo.Personaje;
import adivinaquien.modelo.Pregunta;

import java.util.List;

public class JugadorMaquina extends JugadorBase {

    private final EstrategiaMaquina estrategia;

    public JugadorMaquina(String nombre, List<Personaje> rosterOrdenado, Registro log) {
        this(nombre, rosterOrdenado, log, null);
    }

    public JugadorMaquina(String nombre, List<Personaje> rosterOrdenado, Registro log, EstrategiaMaquina estrategia) {
        super(nombre, rosterOrdenado, log);
        this.estrategia = (estrategia != null) ? estrategia : new EstrategiaGreedy(rastreador, log);
    }

    @Override
    public Accion decidir() {
        log.traza("%s: %d candidatos posibles.", nombre, rastreador.cantidad());

        if (rastreador.cantidad() == 0) {
            log.info("%s: no quedan candidatos consistentes con las respuestas recibidas.", nombre);
            return adivinar(roster.get(0));
        }
        if (rastreador.cantidad() == 1) {
            return adivinar(rastreador.unico());
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
