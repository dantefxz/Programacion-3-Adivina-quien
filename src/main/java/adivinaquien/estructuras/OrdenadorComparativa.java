package adivinaquien.estructuras;

import adivinaquien.datos.RepositorioPersonajes;
import adivinaquien.modelo.ColorPelo;
import adivinaquien.modelo.Comparadores;
import adivinaquien.modelo.Genero;
import adivinaquien.modelo.Personaje;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** Compara merge sort contra quick sort y contra un cuadratico (insercion), en tiempo y estabilidad. */
public final class OrdenadorComparativa {

    private OrdenadorComparativa() {
    }

    public static String informe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comparativa de ordenamientos\n");
        sb.append("(los tiempos son orientativos: dependen de la maquina y del JIT)\n\n");

        sb.append(tablaSobreElTablero());
        sb.append('\n');
        sb.append(tablaListasGrandes());
        sb.append('\n');
        sb.append(estabilidad());
        return sb.toString();
    }

    /** Merge sort vs insercion sobre los 23 personajes del juego: el caso real del TP. */
    private static String tablaSobreElTablero() {
        List<Personaje> tablero = RepositorioPersonajes.crearAgrupadosPorGenero();
        Comparator<Personaje> criterio = Comparadores.POR_ATRIBUTOS_SIN_ID;
        int repeticiones = 200_000;

        long merge = medirMs(repeticiones, () -> MergeSort.ordenar(tablero, criterio));
        long insercion = medirMs(repeticiones, () -> InsertionSort.ordenar(tablero, criterio));

        StringBuilder sb = new StringBuilder();
        sb.append("1) Sobre la lista real de ").append(tablero.size()).append(" personajes\n");
        sb.append("   ").append(repeticiones).append(" ordenamientos de la misma lista:\n");
        sb.append(String.format("     Merge Sort (n log n)   = %5d ms   (%.5f ms por ordenamiento)%n",
                merge, merge / (double) repeticiones));
        sb.append(String.format("     Insercion  (n^2)       = %5d ms   (%.5f ms por ordenamiento)%n",
                insercion, insercion / (double) repeticiones));
        sb.append("   Con n=23 la diferencia es despreciable: ambos ordenan en microsegundos y el\n");
        sb.append("   costo de la partida esta en otro lado. La eleccion de merge sort no se toma\n");
        sb.append("   por velocidad a este tamano sino por su cota garantizada y su estabilidad.\n");
        return sb.toString();
    }

    /** Merge sort vs quick sort en listas grandes: ahi si se nota el orden de crecimiento. */
    private static String tablaListasGrandes() {
        StringBuilder sb = new StringBuilder();
        sb.append("2) Sobre listas grandes (merge sort vs quick sort)\n");
        Random azar = new Random(42);
        for (int n : new int[]{1_000, 20_000, 100_000, 500_000}) {
            List<Personaje> datos = aleatorios(n, azar);
            long merge = medirMs(1, () -> MergeSort.ordenar(datos, Comparadores.POR_ATRIBUTOS_SIN_ID));
            long quick = medirMs(1, () -> QuickSort.ordenar(datos, Comparadores.POR_ATRIBUTOS_SIN_ID));
            sb.append(String.format("   n=%-7d   MergeSort = %5d ms    QuickSort = %5d ms%n", n, merge, quick));
        }
        return sb.toString();
    }

    private static String estabilidad() {
        StringBuilder sb = new StringBuilder();
        sb.append("3) Estabilidad (pares [clave, orden de llegada] ordenados por clave)\n");
        List<int[]> entrada = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            entrada.add(new int[]{i % 3, i});
        }
        Comparator<int[]> porClave = Comparator.comparingInt(par -> par[0]);
        sb.append("   Merge Sort mantiene el orden de llegada: ")
                .append(mantieneOrden(MergeSort.ordenar(entrada, porClave))).append('\n');
        sb.append("   Insercion  mantiene el orden de llegada: ")
                .append(mantieneOrden(InsertionSort.ordenar(entrada, porClave))).append('\n');
        sb.append("   Quick Sort mantiene el orden de llegada: ")
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

    private static long medirMs(int repeticiones, Runnable tarea) {
        long inicio = System.nanoTime();
        for (int i = 0; i < repeticiones; i++) {
            tarea.run();
        }
        return (System.nanoTime() - inicio) / 1_000_000;
    }

    private static List<Personaje> aleatorios(int n, Random azar) {
        ColorPelo[] colores = {ColorPelo.COLORADO, ColorPelo.NEGRO, ColorPelo.AMARILLO};
        Genero[] generos = Genero.values();
        List<Personaje> lista = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            boolean calvo = azar.nextInt(4) == 0;
            ColorPelo color = calvo ? ColorPelo.NINGUNO : colores[azar.nextInt(colores.length)];
            lista.add(new Personaje(i + 1, "P" + i, generos[azar.nextInt(generos.length)], calvo, azar.nextBoolean(), color));
        }
        return lista;
    }
}
