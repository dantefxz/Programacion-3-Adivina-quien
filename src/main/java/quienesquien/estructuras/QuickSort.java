package quienesquien.estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** Quick sort generico con particion de Hoare y pivote al azar. */
public final class QuickSort {

    private static final Random AZAR = new Random();

    private QuickSort() {
    }

    public static <T> List<T> ordenar(List<T> entrada, Comparator<? super T> comparador) {
        List<T> datos = new ArrayList<>(entrada);
        ordenar(datos, 0, datos.size() - 1, comparador);
        return datos;
    }

    private static <T> void ordenar(List<T> a, int lo, int hi, Comparator<? super T> cmp) {
        if (lo >= hi) {
            return;
        }
        int corte = particionar(a, lo, hi, cmp);
        ordenar(a, lo, corte, cmp);
        ordenar(a, corte + 1, hi, cmp);
    }

    private static <T> int particionar(List<T> a, int lo, int hi, Comparator<? super T> cmp) {
        T pivote = a.get(lo + AZAR.nextInt(hi - lo + 1));
        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do {
                i++;
            } while (cmp.compare(a.get(i), pivote) < 0);
            do {
                j--;
            } while (cmp.compare(a.get(j), pivote) > 0);
            if (i >= j) {
                return j;
            }
            Collections.swap(a, i, j);
        }
    }
}
