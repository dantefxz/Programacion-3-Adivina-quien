package quienesquien.servicios;

import quienesquien.interfaces.Registro;
import quienesquien.modelo.Personaje;

import java.util.List;

/** No escribe nada. Para los tests. */
public final class RegistroSilencioso implements Registro {

    @Override
    public void titulo(String formato, Object... args) {
    }

    @Override
    public void info(String formato, Object... args) {
    }

    @Override
    public void traza(String formato, Object... args) {
    }

    @Override
    public void tabla(List<Personaje> personajes) {
    }

    @Override
    public void tablaTraza(List<Personaje> personajes) {
    }

    @Override
    public boolean esVerboso() {
        return false;
    }
}
