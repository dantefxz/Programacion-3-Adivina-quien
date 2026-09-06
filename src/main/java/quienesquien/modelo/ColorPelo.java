package quienesquien.modelo;

/** NINGUNO es el color de los calvos, asi el atributo nunca queda en null. */
public enum ColorPelo {
    COLORADO("colorado"),
    NEGRO("negro"),
    AMARILLO("amarillo"),
    NINGUNO("sin pelo");

    private final String etiqueta;

    ColorPelo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
