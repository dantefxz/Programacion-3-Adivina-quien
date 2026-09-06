package adivinaquien.servicios;

import adivinaquien.interfaces.Accion;
import adivinaquien.interfaces.Registro;
import adivinaquien.modelo.Personaje;

import java.util.List;

public class JugadorHumanoSwing extends JugadorBase {

    private Accion pendiente;

    public JugadorHumanoSwing(String nombre, List<Personaje> rosterOrdenado, Registro log) {
        super(nombre, rosterOrdenado, log);
    }

    public void proponer(Accion accion) {
        this.pendiente = accion;
    }

    @Override
    public Accion decidir() {
        if (pendiente == null) {
            throw new IllegalStateException("No hay accion propuesta para este turno");
        }
        Accion accion = pendiente;
        pendiente = null;
        if (accion instanceof Accion.Adivinar adivinar) {
            ultimaConjetura = adivinar.personaje();
        }
        return accion;
    }
}
