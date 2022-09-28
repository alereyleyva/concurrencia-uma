package impresoras;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaImpresorasML implements SalaImpresoras {

    private List<Integer> impresoras;
    private Lock l = new ReentrantLock(true);
    private Condition okImpresora = l.newCondition();

    public SalaImpresorasML(int N) {
        this.impresoras = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            impresoras.add(i);
        }
    }

    @Override
    public int quieroImpresora(int id) throws InterruptedException {
        System.out.println("Cliente " + id + " quiere impresora");
        l.lock();
        try {
            while (impresoras.size() == 0) {
                okImpresora.await();
            }
            Integer impresoraId = impresoras.get(0);
            impresoras.remove(0);
            System.out.println(
                    "Cliente " + id + " coge impresora " + impresoraId + " quedan libres "
                            + impresoras.size());
            return impresoraId;
        } finally {
            l.unlock();
        }
    }

    @Override
    public void devuelvoImpresora(int id, int n) throws InterruptedException {
        l.lock();
        try {
            impresoras.add(n);
            okImpresora.signal();
        } finally {
            l.unlock();
        }
        System.out.println(
                "Cliente " + id + " devuelve la impresora " + n + " quedan libres "
                        + impresoras.size());
    }

}
