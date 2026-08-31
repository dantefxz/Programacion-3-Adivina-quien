package com.uade.prog3.quienesquien.modelo;

import java.util.function.Predicate;

/** Preguntas de si/no sobre un personaje. */
public enum Pregunta {

    GENERO_FEMENINO("Es de genero femenino?", p -> p.getGenero() == Genero.FEMENINO),
    ES_CALVO("Es calvo?", Personaje::isCalvo),
    USA_LENTES("Usa lentes?", Personaje::isLentes),
    PELO_COLORADO("Tiene el pelo colorado?", p -> p.getColorPelo() == ColorPelo.COLORADO),
    PELO_NEGRO("Tiene el pelo negro?", p -> p.getColorPelo() == ColorPelo.NEGRO),
    PELO_AMARILLO("Tiene el pelo amarillo?", p -> p.getColorPelo() == ColorPelo.AMARILLO);

    private final String texto;
    private final Predicate<Personaje> condicion;

    Pregunta(String texto, Predicate<Personaje> condicion) {
        this.texto = texto;
        this.condicion = condicion;
    }

    public boolean cumple(Personaje p) {
        return condicion.test(p);
    }

    public String getTexto() {
        return texto;
    }
}
