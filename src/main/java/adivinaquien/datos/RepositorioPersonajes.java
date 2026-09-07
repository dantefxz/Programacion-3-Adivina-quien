package adivinaquien.datos;

import adivinaquien.modelo.ColorPelo;
import adivinaquien.modelo.Genero;
import adivinaquien.modelo.Personaje;

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
        personajes.add(conPelo(1, "Godfrey", Genero.MASCULINO, ColorPelo.NEGRO, true));
        personajes.add(calvo(2, "Godrick", Genero.MASCULINO, false));
        personajes.add(conPelo(3, "Radahn", Genero.MASCULINO, ColorPelo.COLORADO, false));
        personajes.add(conPelo(4, "Morgott", Genero.MASCULINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo(5, "Mohg", Genero.MASCULINO, ColorPelo.NEGRO, false));
        personajes.add(calvo(6, "Rykard", Genero.MASCULINO, true));
        personajes.add(conPelo(7, "Radagon", Genero.MASCULINO, ColorPelo.COLORADO, true));
        personajes.add(conPelo(8, "Blaidd", Genero.MASCULINO, ColorPelo.AMARILLO, false));
        personajes.add(conPelo(9, "Bernahl", Genero.MASCULINO, ColorPelo.NEGRO, true));
        personajes.add(calvo(10, "Vyke", Genero.MASCULINO, false));
        personajes.add(conPelo(11, "Gideon", Genero.MASCULINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo(12, "Maliketh", Genero.MASCULINO, ColorPelo.COLORADO, false));

        // Femeninos (11)
        personajes.add(conPelo(13, "Marika", Genero.FEMENINO, ColorPelo.NEGRO, true));
        personajes.add(conPelo(14, "Ranni", Genero.FEMENINO, ColorPelo.AMARILLO, false));
        personajes.add(calvo(15, "Malenia", Genero.FEMENINO, false));
        personajes.add(conPelo(16, "Rennala", Genero.FEMENINO, ColorPelo.COLORADO, true));
        personajes.add(conPelo(17, "Melina", Genero.FEMENINO, ColorPelo.NEGRO, false));
        personajes.add(conPelo(18, "Fia", Genero.FEMENINO, ColorPelo.AMARILLO, true));
        personajes.add(conPelo(19, "Roderika", Genero.FEMENINO, ColorPelo.COLORADO, false));
        personajes.add(calvo(20, "Millicent", Genero.FEMENINO, true));
        personajes.add(conPelo(21, "Nepheli", Genero.FEMENINO, ColorPelo.NEGRO, true));
        personajes.add(conPelo(22, "Sellen", Genero.FEMENINO, ColorPelo.AMARILLO, false));
        personajes.add(conPelo(23, "Rya", Genero.FEMENINO, ColorPelo.COLORADO, true));

        if (personajes.size() != CANTIDAD) {
            throw new IllegalStateException("Deberian ser " + CANTIDAD + " y son " + personajes.size());
        }
        return personajes;
    }

    private static Personaje conPelo(int id, String nombre, Genero genero, ColorPelo color, boolean lentes) {
        return new Personaje(id, nombre, genero, false, lentes, color);
    }

    private static Personaje calvo(int id, String nombre, Genero genero, boolean lentes) {
        return new Personaje(id, nombre, genero, true, lentes, ColorPelo.NINGUNO);
    }
}
