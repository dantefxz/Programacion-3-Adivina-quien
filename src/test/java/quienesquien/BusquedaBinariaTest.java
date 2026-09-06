package quienesquien;

import quienesquien.estructuras.BusquedaBinaria;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusquedaBinariaTest {

    private static final Comparator<Integer> NATURAL = Comparator.naturalOrder();

    @Test
    void posicionDeInsercionInsertaDespuesDeLosIguales() {
        List<Integer> ordenada = List.of(10, 20, 30, 30, 40);
        assertEquals(0, BusquedaBinaria.posicionDeInsercion(ordenada, 5, NATURAL));
        assertEquals(2, BusquedaBinaria.posicionDeInsercion(ordenada, 20, NATURAL));
        assertEquals(4, BusquedaBinaria.posicionDeInsercion(ordenada, 30, NATURAL));
        assertEquals(5, BusquedaBinaria.posicionDeInsercion(ordenada, 100, NATURAL));
    }

    @Test
    void posicionDeInsercionEnListaVacia() {
        assertEquals(0, BusquedaBinaria.posicionDeInsercion(List.of(), 1, NATURAL));
    }

    @Test
    void indiceDeEncuentraYNoEncuentra() {
        List<Integer> ordenada = List.of(1, 3, 5, 7, 9);
        assertEquals(2, BusquedaBinaria.indiceDe(ordenada, 5, NATURAL));
        assertEquals(-1, BusquedaBinaria.indiceDe(ordenada, 6, NATURAL));
    }
}
