import java.util.ArrayList;
import java.util.List;

/**
 * Nodo
 */
public class Nodo extends Thread {

    private List<Integer> lista;

    public Nodo(List<Integer> lista) {
        this.lista = lista;
    }

    public void aniade(List<Integer> l, int desde, int hasta) {
        List<Integer> subLista = lista.subList(desde, hasta + 1);
        l.addAll(subLista);
    }

    public void mezcla(List<Integer> l1, List<Integer> l2) {
        lista.clear();
        int i = 0, j = 0;
        while (i < l1.size() && j < l2.size()) {
            int a = l1.get(i), b = l2.get(j);
            if (a <= b) {
                lista.add(a);
                i++;
            } else {
                lista.add(b);
                j++;
            }
        }
        List<Integer> resto;
        if (i >= l1.size())
            resto = l2.subList(j, l2.size());
        else
            resto = l1.subList(i, l1.size());
        lista.addAll(resto);
    }

    @Override
    public void run() {
        int size = lista.size();
        if (size > 1) {
            int mid = size / 2;

            List<Integer> l1 = new ArrayList<>(1);
            List<Integer> l2 = new ArrayList<>(1);
            aniade(l1, 0, mid - 1);
            aniade(l2, mid, size - 1);

            Nodo n1 = new Nodo(l1);
            Nodo n2 = new Nodo(l2);

            n1.start();
            n2.start();

            try {
                n1.join();
                n2.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            mezcla(n1.lista, n2.lista);
        }
    }
}