package adivinaquien;

import adivinaquien.estructuras.InsertionSort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InsertionSortTest {

    @Test
    void ordenaIgualQueElOrdenNatural() {
        Random azar = new Random(3);
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            datos.add(azar.nextInt(200));
        }
        List<Integer> esperado = new ArrayList<>(datos);
        esperado.sort(Comparator.naturalOrder());

        assertEquals(esperado, InsertionSort.ordenar(datos, Comparator.naturalOrder()));
    }

    @Test
    void noModificaLaEntrada() {
        List<Integer> datos = new ArrayList<>(List.of(3, 1, 2));
        InsertionSort.ordenar(datos, Comparator.naturalOrder());
        assertEquals(List.of(3, 1, 2), datos);
    }

    @Test
    void listasChicasYBorde() {
        assertEquals(List.of(), InsertionSort.ordenar(List.<Integer>of(), Comparator.naturalOrder()));
        assertEquals(List.of(1), InsertionSort.ordenar(List.of(1), Comparator.naturalOrder()));
        assertEquals(List.of(1, 2), InsertionSort.ordenar(List.of(2, 1), Comparator.naturalOrder()));
    }

    @Test
    void esEstable() {
        List<int[]> datos = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            datos.add(new int[]{i % 5, i});
        }
        List<int[]> ordenada = InsertionSort.ordenar(datos, Comparator.comparingInt(p -> p[0]));
        for (int i = 1; i < ordenada.size(); i++) {
            if (ordenada.get(i - 1)[0] == ordenada.get(i)[0]) {
                assertTrue(ordenada.get(i - 1)[1] < ordenada.get(i)[1], "Insercion deberia ser estable");
            }
        }
    }
}
