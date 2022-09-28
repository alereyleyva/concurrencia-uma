package swingpublishbarra;

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
                workerMontecarlo.execute();

                workerSeries = new WorkerSeries(panel);
                workerSeries.addPropertyChangeListener(this);
                workerSeries.execute();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(new JFrame(), "Debes introducir el número de iteraciones", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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
        }
    }
}
