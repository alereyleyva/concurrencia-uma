package impresoras;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SalaImpresorasS implements SalaImpresoras {

    private List<Integer> impresoras;

    private Semaphore permisoImpresora = new Semaphore(1, true);
    private Semaphore mutex = new Semaphore(1, true);

    public SalaImpresorasS(int N) {
        this.impresoras = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            impresoras.add(i);
        }
    }

    @Override
    public int quieroImpresora(int id) throws InterruptedException {
        System.out.println("Cliente " + id + " quiere impresora");

        permisoImpresora.acquire();

        mutex.acquire();

        int impresoraId = impresoras.get(0);
        impresoras.remove(0);

        if (impresoras.size() > 0)
            permisoImpresora.release();

        System.out.println(
                "Cliente " + id + " coge impresora " + impresoraId + " quedan libres " + impresoras.size());
        mutex.release();

        return impresoraId;
    }

    @Override
    public void devuelvoImpresora(int id, int n) throws InterruptedException {
        mutex.acquire();
        impresoras.add(n);
        if (impresoras.size() == 1)
            permisoImpresora.release();
        System.out.println(
                "Cliente " + id + " devuelve la impresora " + n + " quedan libres " + impresoras.size());
        mutex.release();
    }

}
