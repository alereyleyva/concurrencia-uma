package swingworkerdone;

public class Series {

    private int iteraciones = 0;

    private double aproximacion = 0;

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public void aproximate() {
        for (int n = 0; n < iteraciones; n++) {
            aproximacion += (4 * Math.pow(-1, n) / (2 * n + 1));
        }
    }

    public double getAproximacion() {
        return aproximacion;
    }
}
