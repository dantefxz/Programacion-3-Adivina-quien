package adivinaquien.servicios;

import adivinaquien.interfaces.EstrategiaMaquina;
import adivinaquien.interfaces.SalidaTraza;
import adivinaquien.modelo.Personaje;
import adivinaquien.modelo.Pregunta;

import java.util.List;

public class EstrategiaGreedy implements EstrategiaMaquina {

    private final RastreadorCandidatos rastreador;
    private final SalidaTraza log;

    public EstrategiaGreedy(RastreadorCandidatos rastreador, SalidaTraza log) {
        this.rastreador = rastreador;
        this.log = log;
    }

    @Override
    public Pregunta mejorPregunta() {
        List<Personaje> candidatos = rastreador.vista();
        Pregunta mejor = null;
        int mejorPeorCaso = Integer.MAX_VALUE;

        log.traza("   Greedy sobre %d candidatos (minimizar el peor caso):", candidatos.size());
        for (Pregunta pregunta : Pregunta.values()) {
            int si = 0;
            for (Personaje p : candidatos) {
                if (pregunta.cumple(p)) {
                    si++;
                }
            }
            int no = candidatos.size() - si;

            if (si == 0 || no == 0) {
                log.traza("     %-26s si=%2d no=%2d  no divide", pregunta.getTexto(), si, no);
                continue;
            }

            int peorCaso = Math.max(si, no);
            boolean nuevaMejor = peorCaso < mejorPeorCaso;
            if (nuevaMejor) {
                mejorPeorCaso = peorCaso;
                mejor = pregunta;
            }
            log.traza("     %-26s si=%2d no=%2d  peor=%2d%s",
                    pregunta.getTexto(), si, no, peorCaso, nuevaMejor ? "  <- mejor" : "");
        }

        if (mejor != null) {
            log.traza("   => %s (deja como maximo %d)", mejor.getTexto(), mejorPeorCaso);
        } else {
            log.traza("   => ninguna divide, hay que adivinar");
        }
        return mejor;
    }
}
