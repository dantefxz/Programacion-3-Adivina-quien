package quienesquien.servicios;

import quienesquien.estructuras.BusquedaBinaria;
import quienesquien.estructuras.MergeSort;
import quienesquien.interfaces.Registro;
import quienesquien.modelo.Comparadores;
import quienesquien.modelo.Personaje;
import quienesquien.modelo.Pregunta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Personajes que todavia pueden ser el secreto del rival. Se mantiene ordenado
 * por POR_ATRIBUTOS para que descartar() pueda ubicar con busqueda binaria.
 */
public class RastreadorCandidatos {

    private final List<Personaje> candidatos;
    private final Registro log;

    public RastreadorCandidatos(List<Personaje> rosterOrdenado, Registro log) {
        this.candidatos = new ArrayList<>(rosterOrdenado);
        this.log = log;
    }

    public int cantidad() {
        return candidatos.size();
    }

    public List<Personaje> vista() {
        return Collections.unmodifiableList(candidatos);
    }

    /** Candidatos ordenados para mostrar. */
    public List<Personaje> ordenadosParaMostrar() {
        return MergeSort.ordenar(candidatos, Comparadores.POR_ATRIBUTOS);
    }

    public Personaje unico() {
        if (candidatos.size() != 1) {
            throw new IllegalStateException("Quedan " + candidatos.size() + ", no uno");
        }
        return candidatos.get(0);
    }

    /** Saca un personaje del conjunto (adivinanza fallida). */
    public void descartar(Personaje personaje) {
        int i = BusquedaBinaria.indiceDe(candidatos, personaje, Comparadores.POR_ATRIBUTOS);
        if (i >= 0) {
            candidatos.remove(i);
        }
    }

    /** Deja solo los candidatos consistentes con (pregunta -> respuesta). */
    public void filtrar(Pregunta pregunta, boolean respuesta) {
        int antes = candidatos.size();
        candidatos.removeIf(p -> pregunta.cumple(p) != respuesta);
        log.traza("   Filtro \"%s\" = %s  =>  %d candidatos (antes %d)",
                pregunta.getTexto(), respuesta ? "SI" : "NO", candidatos.size(), antes);
        log.tablaTraza(ordenadosParaMostrar());
    }
}
