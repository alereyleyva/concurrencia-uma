package ascensoresSolucion;

import ascensores.Edificio;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EdificioSemaforo1 implements Edificio {

    private Semaphore cogerAscensor = new Semaphore(1, true); // Asumimos que vamos a comenzar con ascensores
    private Semaphore sc = new Semaphore(1, true);
    private int uso[];
    private final int numAscensores;
    private List<Integer> ascensores;

    public EdificioSemaforo1(int numAs) {
        this.numAscensores = numAs;
        ascensores = new LinkedList<Integer>();
        uso = new int[numAscensores];
        for (int i = 0; i < numAscensores; i++) {
            ascensores.add(i);
            uso[i] = 0;
        }

    }

    @Override
    public int boardOnLift(int id) throws InterruptedException {
        cogerAscensor.acquire();
        sc.acquire();
        int idascensor = ascensores.get(0); // Escogo el primero libre
        ascensores.remove(0);

        System.out.println("Persona " + id + " coge el ascensor " + idascensor);

        if (ascensores.size() > 0)
            cogerAscensor.release();
        sc.release();
        // TODO Auto-generated method stub
        return idascensor;
    }

    @Override
    public void boardOffLift(int id, int liftId) throws InterruptedException {
        sc.acquire();
        ascensores.add(liftId);
        uso[liftId] += 1;
        if (ascensores.size() == 1)
            cogerAscensor.release();
        sc.release();

        System.out.println("Persona " + id + " sale del ascensor " + liftId);

    }

    @Override
    public void showUsage() {
        for (int i = 0; i < numAscensores; i++) {
            System.out.println("Ascensor " + i + " usado " + uso[i] + " veces");
        }

    }

}
