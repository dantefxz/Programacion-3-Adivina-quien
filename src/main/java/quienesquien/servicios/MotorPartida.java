package com.uade.prog3.quienesquien.juego;

public class MotorPartida {

    private static final int MAX_TURNOS = 60;

    private final Arbitro arbitro;
    private final Registro log;
    private Jugador actual;
    private Jugador rival;
    private Jugador ganador;
    private Accion ultimaAccion;
    private int turno;

    public MotorPartida(Jugador jugadorA, Jugador jugadorB, Arbitro arbitro, Registro log) {
        this.actual = jugadorA;
        this.rival = jugadorB;
        this.arbitro = arbitro;
        this.log = log;
    }

    public Jugador turnoActual() {
        return actual;
    }

    public Jugador ganador() {
        return ganador;
    }

    public int turno() {
        return turno;
    }

    /** Accion que jugo el ultimo jugador en aplicarTurno(). */
    public Accion ultimaAccion() {
        return ultimaAccion;
    }

    public boolean enCurso() {
        return ganador == null && turno < MAX_TURNOS;
    }

    /** Ejecuta un turno del jugador actual. */
    public void aplicarTurno() {
        if (!enCurso()) {
            return;
        }
        turno++;
        log.titulo("Turno %d - %s", turno, actual.getNombre());

        Accion accion = actual.decidir();
        ultimaAccion = accion;

        if (accion instanceof Accion.Preguntar preguntar) {
            boolean respuesta = arbitro.consultar(rival.getNombre(), preguntar.pregunta());
            log.info("%s pregunta: %s", actual.getNombre(), preguntar.pregunta().getTexto());
            log.info("%s responde: %s", rival.getNombre(), respuesta ? "SI" : "NO");
            actual.recibirRespuestaPregunta(preguntar.pregunta(), respuesta);
        } else {
            Accion.Adivinar adivinar = (Accion.Adivinar) accion;
            boolean acerto = arbitro.esCorrecta(rival.getNombre(), adivinar.personaje());
            log.info("%s adivina: %s -> %s", actual.getNombre(), adivinar.personaje().getNombre(),
                    acerto ? "ACIERTA" : "falla");
            actual.recibirResultadoAdivinanza(acerto);
            if (acerto) {
                ganador = actual;
                log.titulo("Gana %s en el turno %d", actual.getNombre(), turno);
                return;
            }
            log.info("Adivinanza incorrecta: %s pierde el turno.", actual.getNombre());
        }

        Jugador temp = actual;
        actual = rival;
        rival = temp;
    }
}
