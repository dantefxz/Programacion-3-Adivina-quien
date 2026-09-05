package com.uade.prog3.quienesquien.modelo;

/** Un personaje del tablero. El id lo asigna PreparacionJuego antes de ordenar. */
public class Personaje {

    private int id;
    private final String nombre;
    private final Genero genero;
    private final boolean calvo;
    private final boolean lentes;
    private final ColorPelo colorPelo;

    public Personaje(String nombre, Genero genero, boolean calvo, boolean lentes, ColorPelo colorPelo) {
        if (calvo && colorPelo != ColorPelo.NINGUNO) {
            throw new IllegalArgumentException("Un calvo va con ColorPelo.NINGUNO: " + nombre);
        }
        if (!calvo && colorPelo == ColorPelo.NINGUNO) {
            throw new IllegalArgumentException("Un personaje con pelo necesita color: " + nombre);
        }
        this.nombre = nombre;
        this.genero = genero;
        this.calvo = calvo;
        this.lentes = lentes;
        this.colorPelo = colorPelo;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public boolean isCalvo() {
        return calvo;
    }

    public boolean isLentes() {
        return lentes;
    }

    public ColorPelo getColorPelo() {
        return colorPelo;
    }

    /** Linea corta para logs y listados. */
    public String descripcion() {
        String pelo = calvo ? "calvo" : "pelo " + colorPelo.getEtiqueta();
        return String.format("#%2d %-11s | %-9s | %-13s | %s",
                id, nombre, genero.getEtiqueta(), pelo, lentes ? "con lentes" : "sin lentes");
    }

    @Override
    public String toString() {
        return descripcion();
    }

    // Igualdad por id.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof Personaje otro && id == otro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
