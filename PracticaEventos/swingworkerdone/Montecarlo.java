package swingworkerdone;

import java.util.Random;

public class Montecarlo {

    private final static double R = 0.5;

    private int iteraciones = 0;

    private double aproximacion = 0;


    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public void aproximate() {
        double puntosDentro = 0;
        for (int n = 0; n < iteraciones; n++) {
            Punto p = Punto.getPuntoRandom();
            if (p.estaEnCircunferencia()) {
                puntosDentro++;
            }
        }
        aproximacion = iteraciones > 0 ? 4 * puntosDentro / iteraciones : 0;
    }

    public double getAproximacion() {
        return aproximacion;
    }

    public static double getRadio() {
        return R;
    }
}

class Punto {

    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getModulo() {
        return Math.sqrt(Math.pow(x - Montecarlo.getRadio(), 2) + Math.pow(y - Montecarlo.getRadio(), 2));
    }

    public boolean estaEnCircunferencia() {
        return getModulo() < Montecarlo.getRadio();
    }

    public static Punto getPuntoRandom() {
        Random random = new Random();
        double bound = 2 * Montecarlo.getRadio();
        return new Punto(random.nextDouble(bound), random.nextDouble(bound));
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
