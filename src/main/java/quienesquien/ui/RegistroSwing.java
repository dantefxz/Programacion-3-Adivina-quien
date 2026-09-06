package quienesquien.ui;

import quienesquien.interfaces.Registro;
import quienesquien.modelo.Personaje;

import javax.swing.JTextArea;
import java.util.List;

/** Registro que escribe en el area de texto de la pantalla. */
public class RegistroSwing implements Registro {

    private final JTextArea area;
    private final boolean verboso;

    public RegistroSwing(JTextArea area, boolean verboso) {
        this.area = area;
        this.verboso = verboso;
    }

    private void linea(String texto) {
        area.append(texto + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    @Override
    public void titulo(String formato, Object... args) {
        linea("");
        linea("== " + String.format(formato, args) + " ==");
    }

    @Override
    public void info(String formato, Object... args) {
        linea(String.format(formato, args));
    }

    @Override
    public void traza(String formato, Object... args) {
        if (verboso) {
            linea(String.format(formato, args));
        }
    }

    @Override
    public void tabla(List<Personaje> personajes) {
        for (Personaje p : personajes) {
            linea("  " + p.descripcion());
        }
    }

    @Override
    public void tablaTraza(List<Personaje> personajes) {
        if (verboso) {
            tabla(personajes);
        }
    }

    @Override
    public boolean esVerboso() {
        return verboso;
    }
}
