package adivinaquien.interfaces;

import adivinaquien.modelo.Personaje;
import java.util.List;

public interface SalidaJuego {
    void titulo(String formato, Object... args);
    void info(String formato, Object... args);
    void tabla(List<Personaje> personajes);
}