package com.uade.prog3.quienesquien.ui;

import com.uade.prog3.quienesquien.juego.Arbitro;
import com.uade.prog3.quienesquien.juego.Jugador;
import com.uade.prog3.quienesquien.juego.JugadorMaquina;
import com.uade.prog3.quienesquien.juego.MotorPartida;
import com.uade.prog3.quienesquien.juego.PreparacionJuego;
import com.uade.prog3.quienesquien.juego.Registro;
import com.uade.prog3.quienesquien.modelo.Personaje;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;

/** Modo Maquina vs Maquina, con toda la traza a la vista. */
public class PantallaMaquinaVsMaquina extends JPanel {

    private final JTextArea areaLog = new JTextArea(14, 60);
    private final PanelTablero tableroA;
    private final PanelTablero tableroB;
    private final JLabel estado = new JLabel(" ");
    private final JButton botonPaso = new JButton("Siguiente turno");
    private final JButton botonAuto = new JButton("Auto");
    private final Timer timer;

    private final JugadorMaquina maquinaA;
    private final JugadorMaquina maquinaB;
    private final MotorPartida motor;

    public PantallaMaquinaVsMaquina(Runnable volverAlMenu) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        areaLog.setEditable(false);
        areaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        Registro log = new RegistroSwing(areaLog, true);

        List<Personaje> roster = PreparacionJuego.construirRosterOrdenado(log);
        tableroA = new PanelTablero(roster);
        tableroB = new PanelTablero(roster);

        Random azar = new Random();
        Personaje secretoA = roster.get(azar.nextInt(roster.size()));
        Personaje secretoB;
        do {
            secretoB = roster.get(azar.nextInt(roster.size()));
        } while (secretoB.getId() == secretoA.getId());

        maquinaA = new JugadorMaquina("Maquina A", roster, log);
        maquinaB = new JugadorMaquina("Maquina B", roster, log);
        Arbitro arbitro = new Arbitro();
        arbitro.registrarSecreto("Maquina A", secretoA);
        arbitro.registrarSecreto("Maquina B", secretoB);
        motor = new MotorPartida(maquinaA, maquinaB, arbitro, log);

        log.titulo("Secretos (visibles solo aca)");
        log.info("Maquina A tiene: %s", secretoA.descripcion());
        log.info("Maquina B tiene: %s", secretoB.descripcion());

        add(construirTableros(), BorderLayout.CENTER);
        add(construirControles(volverAlMenu), BorderLayout.SOUTH);

        timer = new Timer(900, e -> paso());
        actualizarTableros();
        estado.setText("Turno 0. Apreta \"Siguiente turno\" o \"Auto\".");
    }

    private JPanel construirTableros() {
        JPanel grilla = new JPanel(new GridLayout(1, 2, 12, 0));
        grilla.add(envolver("Maquina A cree que el personaje de B esta entre:", tableroA));
        grilla.add(envolver("Maquina B cree que el personaje de A esta entre:", tableroB));

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.add(grilla, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setPreferredSize(new Dimension(100, 220));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel envolver(String titulo, PanelTablero tablero) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(titulo), BorderLayout.NORTH);
        panel.add(tablero, BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirControles(Runnable volverAlMenu) {
        JPanel panel = new JPanel();
        botonPaso.addActionListener(e -> paso());
        botonAuto.addActionListener(e -> alternarAuto());
        JButton volver = new JButton("Volver al menu");
        volver.addActionListener(e -> {
            timer.stop();
            volverAlMenu.run();
        });
        panel.add(estado);
        panel.add(botonPaso);
        panel.add(botonAuto);
        panel.add(volver);
        return panel;
    }

    private void alternarAuto() {
        if (timer.isRunning()) {
            timer.stop();
            botonAuto.setText("Auto");
        } else {
            timer.start();
            botonAuto.setText("Pausar");
        }
    }

    private void paso() {
        if (!motor.enCurso()) {
            finalizar();
            return;
        }
        motor.aplicarTurno();
        actualizarTableros();
        estado.setText("Turno " + motor.turno());
        if (!motor.enCurso()) {
            finalizar();
        }
    }

    private void actualizarTableros() {
        tableroA.mostrarVivos(maquinaA.candidatosVivos());
        tableroB.mostrarVivos(maquinaB.candidatosVivos());
    }

    private void finalizar() {
        timer.stop();
        botonAuto.setText("Auto");
        botonPaso.setEnabled(false);
        botonAuto.setEnabled(false);
        Jugador ganador = motor.ganador();
        String mensaje = ganador != null
                ? "Gano " + ganador.getNombre() + " en el turno " + motor.turno()
                : "Empate por limite de turnos";
        estado.setText(mensaje);
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
