package com.uade.prog3.quienesquien.ui;

import com.uade.prog3.quienesquien.juego.Accion;
import com.uade.prog3.quienesquien.juego.Arbitro;
import com.uade.prog3.quienesquien.juego.Jugador;
import com.uade.prog3.quienesquien.juego.JugadorHumanoSwing;
import com.uade.prog3.quienesquien.juego.JugadorMaquina;
import com.uade.prog3.quienesquien.juego.MotorPartida;
import com.uade.prog3.quienesquien.juego.PreparacionJuego;
import com.uade.prog3.quienesquien.juego.Registro;
import com.uade.prog3.quienesquien.modelo.Personaje;
import com.uade.prog3.quienesquien.modelo.Pregunta;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/** Modo Jugador vs Maquina. Primero elegis tu personaje; despues preguntas o adivinas. */
public class PantallaHumanoVsMaquina extends JPanel {

    private enum Fase { ELEGIR_SECRETO, JUGANDO, TERMINADA }

    private final List<Personaje> roster;
    private final Registro log;
    private final PanelTablero tablero;
    private final JTextArea areaLog = new JTextArea(10, 40);
    private final JLabel estado = new JLabel(" ");
    private final JLabel infoSecreto = new JLabel("Tu personaje: -");
    private final JLabel infoCandidatos = new JLabel("Posibles: 23");
    private final Map<Pregunta, JButton> botonesPregunta = new EnumMap<>(Pregunta.class);
    private final Set<Pregunta> preguntasUsadasHumano = EnumSet.noneOf(Pregunta.class);
    private final JButton botonAdivinar = new JButton("Adivinar");

    private Fase fase;
    private boolean modoAdivinar;

    private JugadorHumanoSwing humano;
    private JugadorMaquina maquina;
    private MotorPartida motor;
    private Personaje secretoMaquina;

    public PantallaHumanoVsMaquina(Runnable volverAlMenu) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        areaLog.setEditable(false);
        areaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.log = new RegistroSwing(areaLog, false);
        this.roster = PreparacionJuego.construirRosterOrdenado(log);
        this.tablero = new PanelTablero(roster);

        add(construirEncabezado(), BorderLayout.NORTH);
        add(tablero, BorderLayout.CENTER);
        add(construirPanelDerecho(volverAlMenu), BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setPreferredSize(new Dimension(100, 150));
        add(scroll, BorderLayout.SOUTH);

        iniciarEleccionSecreto();
    }

    private JPanel construirEncabezado() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel fila = new JPanel();
        infoSecreto.setFont(infoSecreto.getFont().deriveFont(Font.BOLD));
        fila.add(infoSecreto);
        fila.add(new JLabel("   |   "));
        fila.add(infoCandidatos);
        panel.add(fila);
        panel.add(estado);
        return panel;
    }

    private JPanel construirPanelDerecho(Runnable volverAlMenu) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));

        panel.add(new JLabel("Preguntas:"));
        for (Pregunta pregunta : Pregunta.values()) {
            JButton boton = new JButton(pregunta.getTexto());
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(260, 30));
            boton.addActionListener(e -> preguntar(pregunta));
            botonesPregunta.put(pregunta, boton);
            panel.add(boton);
        }

        panel.add(javax.swing.Box.createVerticalStrut(12));
        botonAdivinar.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonAdivinar.setMaximumSize(new Dimension(260, 30));
        botonAdivinar.addActionListener(e -> alternarModoAdivinar());
        panel.add(botonAdivinar);

        panel.add(javax.swing.Box.createVerticalStrut(12));
        JButton volver = new JButton("Volver al menu");
        volver.setAlignmentX(Component.LEFT_ALIGNMENT);
        volver.setMaximumSize(new Dimension(260, 30));
        volver.addActionListener(e -> volverAlMenu.run());
        panel.add(volver);

        return panel;
    }

    private void iniciarEleccionSecreto() {
        fase = Fase.ELEGIR_SECRETO;
        estado.setText("Elegi tu personaje secreto: clic en una tarjeta.");
        tablero.mostrarVivos(roster);
        tablero.alSeleccionar(this::elegirSecreto);
        tablero.setSeleccionHabilitada(true);
        habilitarControles(false);
    }

    private void elegirSecreto(Personaje elegido) {
        if (fase != Fase.ELEGIR_SECRETO) {
            return;
        }
        secretoMaquina = roster.get(new Random().nextInt(roster.size()));

        humano = new JugadorHumanoSwing("Vos", roster, log);
        maquina = new JugadorMaquina("Maquina", roster, log);
        Arbitro arbitro = new Arbitro();
        arbitro.registrarSecreto("Vos", elegido);
        arbitro.registrarSecreto("Maquina", secretoMaquina);
        motor = new MotorPartida(humano, maquina, arbitro, log);

        infoSecreto.setText("Tu personaje: " + elegido.getNombre());
        log.titulo("Empieza la partida");
        log.info("Elegiste a %s. La maquina ya tiene el suyo (oculto).", elegido.getNombre());

        fase = Fase.JUGANDO;
        tablero.alSeleccionar(this::adivinar);
        tablero.setSeleccionHabilitada(false);
        actualizarTablero();
        habilitarControles(true);
        estado.setText("Tu turno: hace una pregunta o adivina.");
    }

    private void preguntar(Pregunta pregunta) {
        if (fase != Fase.JUGANDO) {
            return;
        }
        marcarPreguntaUsadaHumano(pregunta);
        humano.proponer(new Accion.Preguntar(pregunta));
        avanzar();
    }

    private void marcarPreguntaUsadaHumano(Pregunta pregunta) {
        preguntasUsadasHumano.add(pregunta);
        JButton boton = botonesPregunta.get(pregunta);
        if (boton != null) {
            boton.setEnabled(false);
        }
    }

    private void alternarModoAdivinar() {
        if (fase != Fase.JUGANDO) {
            return;
        }
        modoAdivinar = !modoAdivinar;
        tablero.setSeleccionHabilitada(modoAdivinar);
        botonAdivinar.setText(modoAdivinar ? "Cancelar" : "Adivinar");
        estado.setText(modoAdivinar ? "Clic en el personaje que pensas que tiene la maquina." : "Tu turno.");
    }

    private void adivinar(Personaje elegido) {
        if (fase != Fase.JUGANDO || !modoAdivinar) {
            return;
        }
        modoAdivinar = false;
        botonAdivinar.setText("Adivinar");
        tablero.setSeleccionHabilitada(false);
        humano.proponer(new Accion.Adivinar(elegido));
        avanzar();
    }

    private void avanzar() {
        habilitarControles(false);
        motor.aplicarTurno(); // turno del humano
        actualizarTablero();
        while (motor.enCurso() && motor.turnoActual() == maquina) {
            motor.aplicarTurno();
            actualizarTablero();
        }
        if (!motor.enCurso()) {
            terminar();
        } else {
            estado.setText("Tu turno.");
            habilitarControles(true);
        }
    }

    private void actualizarTablero() {
        tablero.mostrarVivos(humano.candidatosVivos());
        infoCandidatos.setText("Posibles: " + humano.candidatosVivos().size());
    }

    private void terminar() {
        fase = Fase.TERMINADA;
        habilitarControles(false);
        tablero.setSeleccionHabilitada(false);

        Jugador ganador = motor.ganador();
        String mensaje = ganador == humano ? "Ganaste!"
                : ganador == maquina ? "Gano la maquina." : "Empate.";
        estado.setText(mensaje + "  La maquina era " + secretoMaquina.getNombre() + ".");
        tablero.mostrarVivos(List.of(secretoMaquina));
        JOptionPane.showMessageDialog(this, mensaje + "\nLa maquina era: " + secretoMaquina.descripcion());
    }

    private void habilitarControles(boolean habilitados) {
        botonesPregunta.forEach((pregunta, boton) ->
                boton.setEnabled(habilitados && !preguntasUsadasHumano.contains(pregunta)));
        botonAdivinar.setEnabled(habilitados);
    }
}
