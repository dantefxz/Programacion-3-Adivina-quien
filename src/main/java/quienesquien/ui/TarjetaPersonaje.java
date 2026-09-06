package quienesquien.ui;

import quienesquien.modelo.Personaje;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;

/** Una tarjeta del tablero. Si esta descartada se ve tachada y no responde al clic. */
public class TarjetaPersonaje extends JButton {

    private final Personaje personaje;
    private boolean descartado;

    public TarjetaPersonaje(Personaje personaje) {
        this.personaje = personaje;
        setPreferredSize(new Dimension(155, 84));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFocusPainted(false);
        pintar();
    }

    private void pintar() {
        String pelo = personaje.isCalvo() ? "calvo" : "pelo " + personaje.getColorPelo().getEtiqueta();
        String lentes = personaje.isLentes() ? "con lentes" : "sin lentes";
        String cuerpo = "#" + personaje.getId() + " <b>" + personaje.getNombre() + "</b><br>"
                + personaje.getGenero().getEtiqueta() + "<br>" + pelo + "<br>" + lentes;
        if (descartado) {
            cuerpo = "<strike>" + cuerpo + "</strike>";
        }
        setText("<html>" + cuerpo + "</html>");
    }

    public void setDescartado(boolean descartado) {
        this.descartado = descartado;
        setEnabled(!descartado);
        pintar();
    }

    public boolean isDescartado() {
        return descartado;
    }

    public Personaje getPersonaje() {
        return personaje;
    }
}
