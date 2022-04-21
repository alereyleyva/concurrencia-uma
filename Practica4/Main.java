import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int maxRandom = 100;
        Random random = new Random();
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lista.add(random.nextInt(maxRandom));
        }

        System.out.println(lista);

        Nodo n = new Nodo(lista);

        n.start();

        try {
            n.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n ----- Lista ordenada ----- \n");
        System.out.println(lista);
    }
}
