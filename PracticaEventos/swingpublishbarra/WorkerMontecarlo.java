package swingpublishbarra;

import javax.swing.*;
import java.util.List;

public class WorkerMontecarlo extends SwingWorker<Void, Double> {

    private Montecarlo modelInternal;
    private Panel panel;

    private int iteraciones = 0;

    public WorkerMontecarlo(Panel panel) {
        this.panel = panel;
        this.iteraciones = panel.getIteraciones();
        this.modelInternal = new Montecarlo();
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int n = 1; n <= iteraciones; n++) {
            this.modelInternal.anyadirIteracion(n);
            this.setProgress(n * 100 / iteraciones);
            publish(this.modelInternal.getAproximacion());
        }
        return null;
    }

    @Override
    protected void process(List<Double> chunks) {
        panel.escribePI1(chunks.get(chunks.size() - 1));
    }
}
