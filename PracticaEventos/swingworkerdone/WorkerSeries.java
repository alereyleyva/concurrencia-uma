package swingworkerdone;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class WorkerSeries extends SwingWorker<Series, Void> {

    private Series modelInternal;
    private Panel panel;

    public WorkerSeries(Panel panel) {
        this.panel = panel;
        this.modelInternal = new Series();
        this.modelInternal.setIteraciones(panel.getIteraciones());
    }

    @Override
    protected Series doInBackground() throws Exception {
        modelInternal.aproximate();
        return modelInternal;
    }

    @Override
    protected void done() {
        try {
            double aproximacion = this.get().getAproximacion();
            panel.escribePI2(aproximacion);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
