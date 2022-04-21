package Sesion03_25.tercero;

import java.util.Random;

public class Principal {
    public static void main(String[] args) {
        Random r = new Random();
        int[] v;
        boolean[] b = { true, true };
        v = new int[r.nextInt(10)];
        for (int i = 0; i < v.length; i++) {
            v[i] = r.nextInt(2);
            System.out.print(v[i] + "  ");
        }
        Hebra h1 = new Hebra(v, 0, v.length / 2, b, 0);
        Hebra h2 = new Hebra(v, v.length / 2, v.length, b, 1);
        h1.start();
        h2.start();
        try {
            h1.join();
            h2.join();
        } catch (InterruptedException ie) {
        }
        // while (h1.isAlive()); //espera activa
        // while (h2.isAlive()); //espera activa

        System.out.println(b[0] && b[1]);
    }

}
