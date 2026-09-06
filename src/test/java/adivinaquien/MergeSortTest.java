package adivinaquien;

import adivinaquien.estructuras.MergeSort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeSortTest {

    @Test
    void ordenaIgualQueElOrdenNatural() {
        Random azar = new Random(1);
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            datos.add(azar.nextInt(500));
        }
        List<Integer> esperado = new ArrayList<>(datos);
        esperado.sort(Comparator.naturalOrder());

        assertEquals(esperado, MergeSort.ordenar(datos, Comparator.naturalOrder()));
    }

    @Test
    void noModificaLaEntrada() {
        List<Integer> datos = new ArrayList<>(List.of(3, 1, 2));
        MergeSort.ordenar(datos, Comparator.naturalOrder());
        assertEquals(List.of(3, 1, 2), datos);
    }

    @Test
    void esEstable() {
        // pares [clave, ordenDeLlegada]; al ordenar por clave, el orden de llegada debe quedar creciente
        List<int[]> datos = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            datos.add(new int[]{i % 5, i});
        }
        List<int[]> ordenada = MergeSort.ordenar(datos, Comparator.comparingInt(p -> p[0]));
        for (int i = 1; i < ordenada.size(); i++) {
            if (ordenada.get(i - 1)[0] == ordenada.get(i)[0]) {
                assertTrue(ordenada.get(i - 1)[1] < ordenada.get(i)[1], "Merge Sort deberia ser estable");
            }
        }
    }
}
