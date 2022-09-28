package swingpublishbarra;

public class Series {

    private double aproximacion = 0;
    
    public static double getSumando(int n) {
        return (4 * Math.pow(-1, n) / (2 * n + 1));
    }

    public void anyadirIteracion(int n) {
        aproximacion += getSumando(n);
    }

    public double getAproximacion() {
        return aproximacion;
    }
}
