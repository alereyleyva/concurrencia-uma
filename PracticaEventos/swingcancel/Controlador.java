package swingcancel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controlador implements ActionListener, PropertyChangeListener {

    private Panel panel;

    private WorkerMontecarlo workerMontecarlo;

    private WorkerSeries workerSeries;

    public Controlador(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Panel.COMENZAR())) {
            try {
                workerMontecarlo = new WorkerMontecarlo(panel);
                workerMontecarlo.addPropertyChangeListener(this);
                workerSeries = new WorkerSeries(panel);
                workerSeries.addPropertyChangeListener(this);

                panel.resetearProgreso();
                panel.setBotonCancelar();
                workerMontecarlo.execute();
                workerSeries.execute();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(new JFrame(), "Debes introducir el número de iteraciones", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals(Panel.CANCELAR())) {
            if (workerMontecarlo != null) workerMontecarlo.cancel(true);
            if (workerSeries != null) workerSeries.cancel(true);
            panel.setBotonComenzar();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            if (evt.getSource().equals(workerMontecarlo)) {
                panel.setProgresoMonteCarlo((Integer) evt.getNewValue());
            } else {
                panel.setProgresoLeibniz((Integer) evt.getNewValue());
            }

            if (panel.procesoTerminado()) {
                panel.setBotonComenzar();
            }
        }
    }
}
