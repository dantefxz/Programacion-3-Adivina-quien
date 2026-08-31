package com.uade.prog3.quienesquien.estructuras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Merge sort generico y estable. No modifica la lista de entrada. */
public final class MergeSort {

    private MergeSort() {
    }

    public static <T> List<T> ordenar(List<T> entrada, Comparator<? super T> comparador) {
        List<T> datos = new ArrayList<>(entrada);
        if (datos.size() < 2) {
            return datos;
        }
        List<T> auxiliar = new ArrayList<>(datos);
        ordenar(datos, auxiliar, 0, datos.size() - 1, comparador);
        return datos;
    }

    private static <T> void ordenar(List<T> a, List<T> aux, int lo, int hi, Comparator<? super T> cmp) {
        if (lo >= hi) {
            return;
        }
        int medio = lo + (hi - lo) / 2;
        ordenar(a, aux, lo, medio, cmp);
        ordenar(a, aux, medio + 1, hi, cmp);
        combinar(a, aux, lo, medio, hi, cmp);
    }

    private static <T> void combinar(List<T> a, List<T> aux, int lo, int medio, int hi, Comparator<? super T> cmp) {
        for (int k = lo; k <= hi; k++) {
            aux.set(k, a.get(k));
        }
        int i = lo;
        int j = medio + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > medio) {
                a.set(k, aux.get(j++));
            } else if (j > hi) {
                a.set(k, aux.get(i++));
            } else if (cmp.compare(aux.get(j), aux.get(i)) < 0) {
                a.set(k, aux.get(j++));
            } else {
                a.set(k, aux.get(i++)); // en empate va primero el de la izquierda
            }
        }
    }
}
