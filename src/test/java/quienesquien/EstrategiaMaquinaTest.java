package quienesquien;

import quienesquien.servicios.EstrategiaGreedy;
import quienesquien.servicios.PreparacionJuego;
import quienesquien.servicios.RastreadorCandidatos;
import quienesquien.interfaces.Registro;
import quienesquien.servicios.RegistroSilencioso;
import quienesquien.modelo.Personaje;
import quienesquien.modelo.Pregunta;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstrategiaMaquinaTest {

    private static final Registro SILENCIO = new RegistroSilencioso();

    private static List<Personaje> rosterOrdenado() {
        return PreparacionJuego.construirRosterOrdenado(SILENCIO);
    }

    @Test
    void laPrimeraPreguntaParteElConjuntoCasiALaMitad() {
        RastreadorCandidatos rastreador = new RastreadorCandidatos(rosterOrdenado(), SILENCIO);
        Pregunta elegida = new EstrategiaGreedy(rastreador, SILENCIO).mejorPregunta();
        assertNotNull(elegida);

        int si = 0;
        for (Personaje p : rastreador.vista()) {
            if (elegida.cumple(p)) {
                si++;
            }
        }
        int no = rastreador.vista().size() - si;
        assertTrue(Math.max(si, no) <= 12, "el peor lado deberia ser <= 12 y fue " + Math.max(si, no));
    }

    @Test
    void filtrarReduceElConjunto() {
        RastreadorCandidatos rastreador = new RastreadorCandidatos(rosterOrdenado(), SILENCIO);
        rastreador.filtrar(Pregunta.GENERO_FEMENINO, true);
        assertEquals(11, rastreador.cantidad());
    }
}
