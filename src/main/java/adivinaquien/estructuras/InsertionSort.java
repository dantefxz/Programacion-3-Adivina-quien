package adivinaquien.estructuras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Ordenamiento por insercion: cuadratico y estable. Sirve de termino de comparacion frente a merge sort. */
public final class InsertionSort {

    private InsertionSort() {
    }

    public static <T> List<T> ordenar(List<T> entrada, Comparator<? super T> comparador) {
        List<T> datos = new ArrayList<>(entrada);
        for (int i = 1; i < datos.size(); i++) {
            T actual = datos.get(i);
            int j = i - 1;
            while (j >= 0 && comparador.compare(datos.get(j), actual) > 0) {
                datos.set(j + 1, datos.get(j));
                j--;
            }
            datos.set(j + 1, actual);
        }
        return datos;
    }
}
