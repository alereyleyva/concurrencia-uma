package swingcancel;

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
        int n = 0;
        while (n < iteraciones && !isCancelled()) {
            Thread.sleep(20);
            modelInternal.anyadirIteracion(n);
            this.setProgress((n + 1) * 100 / iteraciones);
            publish(modelInternal.getAproximacion());
            n++;
        }

        return null;
    }

    @Override
    protected void process(List<Double> chunks) {
        panel.escribePI2(chunks.get(chunks.size() - 1));
    }
}
