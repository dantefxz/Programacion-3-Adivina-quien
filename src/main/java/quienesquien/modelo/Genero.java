package quienesquien.modelo;

public enum Genero {
    MASCULINO("masculino"),
    FEMENINO("femenino");

    private final String etiqueta;

    Genero(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
