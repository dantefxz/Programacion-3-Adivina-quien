package quienesquien.ui;

import quienesquien.modelo.Personaje;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** Grilla con los personajes. Marca cuales siguen en pie y avisa al seleccionar uno. */
public class PanelTablero extends JPanel {

    private final List<TarjetaPersonaje> tarjetas = new ArrayList<>();
    private Consumer<Personaje> alSeleccionar;
    private boolean seleccionHabilitada;

    public PanelTablero(List<Personaje> roster) {
        setLayout(new GridLayout(0, 6, 6, 6));
        for (Personaje p : roster) {
            TarjetaPersonaje tarjeta = new TarjetaPersonaje(p);
            tarjeta.addActionListener(e -> {
                if (seleccionHabilitada && alSeleccionar != null) {
                    alSeleccionar.accept(p);
                }
            });
            tarjetas.add(tarjeta);
            add(tarjeta);
        }
    }

    public void mostrarVivos(Collection<Personaje> vivos) {
        Set<Integer> ids = vivos.stream().map(Personaje::getId).collect(Collectors.toCollection(HashSet::new));
        for (TarjetaPersonaje tarjeta : tarjetas) {
            tarjeta.setDescartado(!ids.contains(tarjeta.getPersonaje().getId()));
        }
    }

    public void alSeleccionar(Consumer<Personaje> handler) {
        this.alSeleccionar = handler;
    }

    public void setSeleccionHabilitada(boolean habilitada) {
        this.seleccionHabilitada = habilitada;
    }
}
