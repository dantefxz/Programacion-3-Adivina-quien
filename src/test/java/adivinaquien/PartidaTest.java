package adivinaquien;

import adivinaquien.interfaces.Arbitro;
import adivinaquien.servicios.ArbitroClasico;
import adivinaquien.interfaces.Jugador;
import adivinaquien.servicios.JugadorMaquina;
import adivinaquien.servicios.MotorPartida;
import adivinaquien.servicios.PreparacionJuego;
import adivinaquien.interfaces.Registro;
import adivinaquien.servicios.RegistroSilencioso;
import adivinaquien.modelo.Personaje;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PartidaTest {

    private static final Registro SILENCIO = new RegistroSilencioso();

    private static List<Personaje> rosterOrdenado() {
        return PreparacionJuego.construirRosterOrdenado(SILENCIO);
    }

    @Test
    void maquinaVsMaquinaTerminaConGanadorYEnPocosTurnos() {
        Random azar = new Random(123);
        long sumaTurnos = 0;
        int corridas = 300;

        for (int i = 0; i < corridas; i++) {
            List<Personaje> roster = rosterOrdenado();
            Personaje secretoA = roster.get(azar.nextInt(roster.size()));
            Personaje secretoB = roster.get(azar.nextInt(roster.size()));

            Jugador a = new JugadorMaquina("Maquina A", roster, SILENCIO);
            Jugador b = new JugadorMaquina("Maquina B", roster, SILENCIO);
            Arbitro arbitro = new ArbitroClasico();
            arbitro.registrarSecreto("Maquina A", secretoA);
            arbitro.registrarSecreto("Maquina B", secretoB);

            MotorPartida motor = new MotorPartida(a, b, arbitro, SILENCIO);
            while (motor.enCurso()) {
                motor.aplicarTurno();
            }

            assertNotNull(motor.ganador(), "deberia haber ganador");
            assertTrue(motor.turno() <= 20, "demasiados turnos: " + motor.turno());
            sumaTurnos += motor.turno();
        }

        assertTrue(sumaTurnos / (double) corridas < 14, "promedio de turnos alto");
    }
}
