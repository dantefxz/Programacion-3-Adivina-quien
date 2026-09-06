package quienesquien.ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/** Ventana principal. Cambia entre el menu y cada modo con un CardLayout. */
public class VentanaPrincipal extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel contenedor = new JPanel(cards);
    private final JPanel menu;

    public VentanaPrincipal() {
        setTitle("Quien es Quien");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));
        setSize(1150, 800);
        setLocationRelativeTo(null);

        menu = construirMenu();
        contenedor.add(menu, "menu");
        setContentPane(contenedor);
        cards.show(contenedor, "menu");
    }

    private JPanel construirMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel titulo = new JLabel("Quien es Quien");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 28f));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(30));

        panel.add(botonMenu("Jugador vs Maquina",
                () -> abrir(new PantallaHumanoVsMaquina(this::volverAlMenu))));
        panel.add(Box.createVerticalStrut(12));
        panel.add(botonMenu("Maquina vs Maquina (con traza)",
                () -> abrir(new PantallaMaquinaVsMaquina(this::volverAlMenu))));
        panel.add(Box.createVerticalStrut(12));
        panel.add(botonMenu("Comparativa Merge Sort vs Quick Sort",
                () -> abrir(new PanelComparativa(this::volverAlMenu))));

        return panel;
    }

    private JButton botonMenu(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(360, 44));
        boton.addActionListener(e -> accion.run());
        return boton;
    }

    private void abrir(JComponent pantalla) {
        contenedor.add(pantalla, "juego");
        cards.show(contenedor, "juego");
    }

    private void volverAlMenu() {
        for (Component c : contenedor.getComponents()) {
            if (c != menu) {
                contenedor.remove(c);
            }
        }
        cards.show(contenedor, "menu");
        revalidate();
        repaint();
    }
}
