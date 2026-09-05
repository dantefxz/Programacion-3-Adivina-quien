package com.uade.prog3.quienesquien.datos;

import com.uade.prog3.quienesquien.modelo.ColorPelo;
import com.uade.prog3.quienesquien.modelo.Genero;
import com.uade.prog3.quienesquien.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Los 23 personajes del juego, agrupados por genero y sin id. Con 16 combinaciones
 * posibles de atributos, siempre quedan algunos indistinguibles entre si.
 */
public final class RepositorioPersonajes {

    public static final int CANTIDAD = 23;

    private RepositorioPersonajes() {
    }

    public static List<Personaje> crearAgrupadosPorGenero() {
        List<Personaje> personajes = new ArrayList<>();

        // Masculinos (12)
        personajes.add(conPelo("Godfrey", Genero.MASCULINO, ColorPelo.NEGRO, true));
        personajes.add(calvo("Godrick", Genero.MASCULINO, false));
        personajes.add(conPelo("Radahn", Genero.MASCULINO, ColorPelo.COLORADO, false));
        personajes.add(conPelo("Morgott", Genero.MASCULINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo("Mohg", Genero.MASCULINO, ColorPelo.NEGRO, false));
        personajes.add(calvo("Rykard", Genero.MASCULINO, true));
        personajes.add(conPelo("Radagon", Genero.MASCULINO, ColorPelo.COLORADO, true));
        personajes.add(conPelo("Blaidd", Genero.MASCULINO, ColorPelo.AMARILLO, false));
        personajes.add(conPelo("Bernahl", Genero.MASCULINO, ColorPelo.NEGRO, true));
        personajes.add(calvo("Vyke", Genero.MASCULINO, false));
        personajes.add(conPelo("Gideon", Genero.MASCULINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo("Maliketh", Genero.MASCULINO, ColorPelo.COLORADO, false));

        // Femeninos (11)
        personajes.add(conPelo("Marika", Genero.FEMENINO, ColorPelo.NEGRO, true));
        personajes.add(conPelo("Ranni", Genero.FEMENINO, ColorPelo.AMARILLO, false));
        personajes.add(calvo("Malenia", Genero.FEMENINO, false));
        personajes.add(conPelo("Rennala", Genero.FEMENINO, ColorPelo.COLORADO, true));
        personajes.add(conPelo("Melina", Genero.FEMENINO, ColorPelo.NEGRO, false));
        personajes.add(conPelo("Fia", Genero.FEMENINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo("Roderika", Genero.FEMENINO, ColorPelo.COLORADO, false));
        personajes.add(calvo("Millicent", Genero.FEMENINO, true));
        personajes.add(conPelo("Nepheli", Genero.FEMENINO, ColorPelo.NEGRO, true));
        personajes.add(conPelo("Sellen", Genero.FEMENINO, ColorPelo.AMARILLO, false));
        personajes.add(conPelo("Rya", Genero.FEMENINO, ColorPelo.COLORADO, true));

        if (personajes.size() != CANTIDAD) {
            throw new IllegalStateException("Deberian ser " + CANTIDAD + " y son " + personajes.size());
        }
        return personajes;
    }

    private static Personaje conPelo(String nombre, Genero genero, ColorPelo color, boolean lentes) {
        return new Personaje(nombre, genero, false, lentes, color);
    }

    private static Personaje calvo(String nombre, Genero genero, boolean lentes) {
        return new Personaje(nombre, genero, true, lentes, ColorPelo.NINGUNO);
    }
}
