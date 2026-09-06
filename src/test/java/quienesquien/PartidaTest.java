package quienesquien;

import quienesquien.interfaces.Arbitro;
import quienesquien.servicios.ArbitroClasico;
import quienesquien.interfaces.Jugador;
import quienesquien.servicios.JugadorMaquina;
import quienesquien.servicios.MotorPartida;
import quienesquien.servicios.PreparacionJuego;
import quienesquien.interfaces.Registro;
import quienesquien.servicios.RegistroSilencioso;
import quienesquien.modelo.Personaje;
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
