package quienesquien.ui;

import quienesquien.estructuras.OrdenadorComparativa;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Font;

/** Corre la comparativa Merge vs Quick y muestra el informe. */
public class PanelComparativa extends JPanel {

    private final JTextArea area = new JTextArea();
    private final JButton botonEjecutar = new JButton("Ejecutar comparativa");

    public PanelComparativa(Runnable volverAlMenu) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setText("Apreta \"Ejecutar comparativa\".\nOrdena listas grandes con los dos algoritmos, puede tardar unos segundos.");

        JPanel barra = new JPanel();
        botonEjecutar.addActionListener(e -> ejecutar());
        JButton volver = new JButton("Volver al menu");
        volver.addActionListener(e -> volverAlMenu.run());
        barra.add(botonEjecutar);
        barra.add(volver);

        add(barra, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    private void ejecutar() {
        botonEjecutar.setEnabled(false);
        area.setText("Calculando...");
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return OrdenadorComparativa.informe();
            }

            @Override
            protected void done() {
                try {
                    area.setText(get());
                } catch (Exception e) {
                    area.setText("Error: " + e.getMessage());
                }
                botonEjecutar.setEnabled(true);
            }
        }.execute();
    }
}
