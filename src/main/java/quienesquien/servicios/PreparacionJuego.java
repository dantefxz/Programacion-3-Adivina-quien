package quienesquien.servicios;

import quienesquien.datos.RepositorioPersonajes;
import quienesquien.estructuras.MergeSort;
import quienesquien.interfaces.Registro;
import quienesquien.modelo.Comparadores;
import quienesquien.modelo.Personaje;

import java.util.List;

/** Asigna id a los personajes y los ordena por atributos con merge sort. */
public final class PreparacionJuego {

    private PreparacionJuego() {
    }

    public static List<Personaje> construirRosterOrdenado(Registro log) {
        List<Personaje> personajes = RepositorioPersonajes.crearAgrupadosPorGenero();

        int id = 1;
        for (Personaje p : personajes) {
            p.asignarId(id++);
        }

        log.titulo("La maquina ordena los %d personajes", personajes.size());
        log.traza("Entrada (solo agrupada por genero):");
        log.tablaTraza(personajes);

        List<Personaje> ordenado = MergeSort.ordenar(personajes, Comparadores.POR_ATRIBUTOS);

        log.info("Lista ordenada (merge sort):");
        log.tabla(ordenado);

        return ordenado;
    }
}
