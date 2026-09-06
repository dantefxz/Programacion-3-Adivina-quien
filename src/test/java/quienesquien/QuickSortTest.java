package quienesquien;

import quienesquien.estructuras.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuickSortTest {

    @Test
    void ordenaIgualQueElOrdenNatural() {
        Random azar = new Random(2);
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            datos.add(azar.nextInt(1000));
        }
        List<Integer> esperado = new ArrayList<>(datos);
        esperado.sort(Comparator.naturalOrder());

        assertEquals(esperado, QuickSort.ordenar(datos, Comparator.naturalOrder()));
    }

    @Test
    void soportaListaConTodosLosElementosIguales() {
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            datos.add(7);
        }
        List<Integer> ordenada = QuickSort.ordenar(datos, Comparator.naturalOrder());
        assertEquals(500, ordenada.size());
        assertEquals(7, ordenada.get(0));
        assertEquals(7, ordenada.get(499));
    }

    @Test
    void listasChicasYBorde() {
        assertEquals(List.of(), QuickSort.ordenar(List.<Integer>of(), Comparator.naturalOrder()));
        assertEquals(List.of(1), QuickSort.ordenar(List.of(1), Comparator.naturalOrder()));
        assertEquals(List.of(1, 2), QuickSort.ordenar(List.of(2, 1), Comparator.naturalOrder()));
    }
}
