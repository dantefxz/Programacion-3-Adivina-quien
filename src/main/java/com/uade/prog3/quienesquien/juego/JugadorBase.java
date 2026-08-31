package com.uade.prog3.quienesquien.juego;

import com.uade.prog3.quienesquien.modelo.Personaje;
import com.uade.prog3.quienesquien.modelo.Pregunta;

import java.util.List;

public abstract class JugadorBase implements Jugador {

    protected final String nombre;
    protected final List<Personaje> roster;
    protected final RastreadorCandidatos rastreador;
    protected final Registro log;
    protected Personaje ultimaConjetura;

    protected JugadorBase(String nombre, List<Personaje> rosterOrdenado, Registro log) {
        this.nombre = nombre;
        this.roster = List.copyOf(rosterOrdenado);
        this.rastreador = new RastreadorCandidatos(rosterOrdenado, log);
        this.log = log;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void recibirRespuestaPregunta(Pregunta pregunta, boolean respuesta) {
        rastreador.filtrar(pregunta, respuesta);
    }

    @Override
    public void recibirResultadoAdivinanza(boolean acerto) {
        if (!acerto && ultimaConjetura != null) {
            rastreador.descartar(ultimaConjetura);
        }
    }

    /** lo usa la GUI para dibujar el tablero. */
    public List<Personaje> candidatosVivos() {
        return rastreador.vista();
    }

    protected Personaje personajePorId(int id) {
        for (Personaje p : roster) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("No hay personaje con id " + id);
    }
}
