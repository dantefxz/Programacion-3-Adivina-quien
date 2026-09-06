package quienesquien.estructuras;

import java.util.Comparator;
import java.util.List;

public final class BusquedaBinaria {

    private BusquedaBinaria() {
    }

    /** Posicion donde insertar la clave sin romper el orden */
    public static <T> int posicionDeInsercion(List<T> ordenada, T clave, Comparator<? super T> cmp) {
        return posicionDeInsercion(ordenada, clave, cmp, 0, ordenada.size());
    }

    private static <T> int posicionDeInsercion(List<T> a, T clave, Comparator<? super T> cmp, int lo, int hi) {
        if (lo >= hi) {
            return lo;
        }
        int medio = (lo + hi) >>> 1;
        if (cmp.compare(a.get(medio), clave) <= 0) {
            return posicionDeInsercion(a, clave, cmp, medio + 1, hi);
        }
        return posicionDeInsercion(a, clave, cmp, lo, medio);
    }

    /** Indice de un elemento igual a la clave, o -1. */
    public static <T> int indiceDe(List<T> ordenada, T clave, Comparator<? super T> cmp) {
        return indiceDe(ordenada, clave, cmp, 0, ordenada.size() - 1);
    }

    private static <T> int indiceDe(List<T> a, T clave, Comparator<? super T> cmp, int lo, int hi) {
        if (lo > hi) {
            return -1;
        }
        int medio = (lo + hi) >>> 1;
        int c = cmp.compare(a.get(medio), clave);
        if (c == 0) {
            return medio;
        }
        if (c < 0) {
            return indiceDe(a, clave, cmp, medio + 1, hi);
        }
        return indiceDe(a, clave, cmp, lo, medio - 1);
    }
}
