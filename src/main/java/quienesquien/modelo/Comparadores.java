package com.uade.prog3.quienesquien.modelo;

import java.util.Comparator;

/** Criterios de orden para los personajes. */
public final class Comparadores {

    private Comparadores() {
    }

    /** Orden por atributos, sin desempate. */
    public static final Comparator<Personaje> POR_ATRIBUTOS_SIN_ID =
            Comparator.comparing(Personaje::getGenero)
                    .thenComparing(Personaje::isCalvo)
                    .thenComparing(Personaje::getColorPelo)
                    .thenComparing(Personaje::isLentes);

    /** Orden total y determinista: agrega el id como ultimo criterio. */
    public static final Comparator<Personaje> POR_ATRIBUTOS =
            POR_ATRIBUTOS_SIN_ID.thenComparingInt(Personaje::getId);
}
