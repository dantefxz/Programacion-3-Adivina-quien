package adivinaquien.interfaces;

import adivinaquien.modelo.Personaje;
import java.util.List;

public interface SalidaTraza {
    void traza(String formato, Object... args);
    void tablaTraza(List<Personaje> personajes);
}