package swingpublishbarra;

import javax.swing.*;
import java.util.List;

public class WorkerSeries extends SwingWorker<Void, Double> {

    private Series modelInternal;
    private Panel panel;

    private int iteraciones;

    public WorkerSeries(Panel panel) {
        this.panel = panel;
        this.iteraciones = panel.getIteraciones();
        this.modelInternal = new Series();
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int n = 0; n < iteraciones; n++) {
            modelInternal.anyadirIteracion(n);
            this.setProgress((n + 1) * 100 / iteraciones);
            publish(modelInternal.getAproximacion());
        }
        return null;
    }

    @Override
    protected void process(List<Double> chunks) {
        panel.escribePI2(chunks.get(chunks.size() - 1));
    }
}
