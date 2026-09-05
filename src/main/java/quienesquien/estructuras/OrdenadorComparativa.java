package com.uade.prog3.quienesquien.estructuras;

import com.uade.prog3.quienesquien.modelo.ColorPelo;
import com.uade.prog3.quienesquien.modelo.Comparadores;
import com.uade.prog3.quienesquien.modelo.Genero;
import com.uade.prog3.quienesquien.modelo.Personaje;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** Compara merge sort y quick sort en tiempo y estabilidad, y devuelve el informe como texto. */
public final class OrdenadorComparativa {

    private OrdenadorComparativa() {
    }

    public static String informe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comparativa Merge Sort vs Quick Sort\n");
        sb.append("(los tiempos son orientativos: dependen de la maquina y del JIT)\n\n");

        Random azar = new Random(42);
        for (int n : new int[]{1_000, 20_000, 100_000, 500_000}) {
            List<Personaje> datos = aleatorios(n, azar);
            long merge = medirMs(() -> MergeSort.ordenar(datos, Comparadores.POR_ATRIBUTOS_SIN_ID));
            long quick = medirMs(() -> QuickSort.ordenar(datos, Comparadores.POR_ATRIBUTOS_SIN_ID));
            sb.append(String.format("n=%-7d   MergeSort = %5d ms    QuickSort = %5d ms%n", n, merge, quick));
        }

        sb.append('\n');
        sb.append("Estabilidad (pares [clave, orden de llegada] ordenados por clave):\n");
        List<int[]> entrada = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            entrada.add(new int[]{i % 3, i});
        }
        Comparator<int[]> porClave = Comparator.comparingInt(par -> par[0]);
        sb.append("  Merge Sort mantiene el orden de llegada: ")
                .append(mantieneOrden(MergeSort.ordenar(entrada, porClave))).append('\n');
        sb.append("  Quick Sort mantiene el orden de llegada: ")
                .append(mantieneOrden(QuickSort.ordenar(entrada, porClave))).append('\n');
        return sb.toString();
    }

    private static boolean mantieneOrden(List<int[]> ordenada) {
        for (int i = 1; i < ordenada.size(); i++) {
            if (ordenada.get(i - 1)[0] == ordenada.get(i)[0] && ordenada.get(i - 1)[1] > ordenada.get(i)[1]) {
                return false;
            }
        }
        return true;
    }

    private static long medirMs(Runnable tarea) {
        long inicio = System.nanoTime();
        tarea.run();
        return (System.nanoTime() - inicio) / 1_000_000;
    }

    private static List<Personaje> aleatorios(int n, Random azar) {
        ColorPelo[] colores = {ColorPelo.COLORADO, ColorPelo.NEGRO, ColorPelo.AMARILLO};
        Genero[] generos = Genero.values();
        List<Personaje> lista = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            boolean calvo = azar.nextInt(4) == 0;
            ColorPelo color = calvo ? ColorPelo.NINGUNO : colores[azar.nextInt(colores.length)];
            lista.add(new Personaje("P" + i, generos[azar.nextInt(generos.length)], calvo, azar.nextBoolean(), color));
        }
        return lista;
    }
}
