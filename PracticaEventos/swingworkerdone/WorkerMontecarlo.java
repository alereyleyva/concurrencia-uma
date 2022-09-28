package swingworkerdone;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class WorkerMontecarlo extends SwingWorker<Montecarlo, Void> {

    private Montecarlo modelInternal;
    private Panel panel;

    public WorkerMontecarlo(Panel panel) {
        this.panel = panel;
        this.modelInternal = new Montecarlo();
        this.modelInternal.setIteraciones(panel.getIteraciones());
    }

    @Override
    protected Montecarlo doInBackground() throws Exception {
        modelInternal.aproximate();
        return modelInternal;
    }

    @Override
    protected void done() {
        try {
            double aproximacion = this.get().getAproximacion();
            panel.escribePI1(aproximacion);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
