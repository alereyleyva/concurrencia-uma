package swingworkerdone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

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
                workerMontecarlo.execute();

                workerSeries = new WorkerSeries(panel);
                workerSeries.execute();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(new JFrame(), "Debes introducir el número de iteraciones", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
