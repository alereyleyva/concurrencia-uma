package ascensoresSolucion;

import ascensores.Edificio;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EdificioSemaforo implements Edificio {

    private Semaphore colaEntrada = new Semaphore(1, true);
    private Semaphore esperaAscensor = new Semaphore(0, true);
    private Semaphore sc = new Semaphore(1, true);

    private int uso[];
    private final int numAscensores;
    private List<Integer> ascensores;
    private boolean hayEsperando;

    public EdificioSemaforo(int numAs) {
        this.numAscensores = numAs;
        ascensores = new LinkedList<Integer>();
        uso = new int[numAscensores];
        for (int i = 0; i < numAscensores; i++) {
            ascensores.add(i);
            uso[i] = 0;
        }
        hayEsperando = false;
    }

    @Override
    public int boardOnLift(int id) throws InterruptedException {
        colaEntrada.acquire();
        sc.acquire();
        if (ascensores.size() == 0) { // No hay disponibles, me duermo y duermo a todo el que espera en colaEntrada
            hayEsperando = true;
            sc.release();
            esperaAscensor.acquire();
            sc.acquire();
        }
        int idascensor = ascensores.get(0); // Escogo el primero libre
        ascensores.remove(0);
        sc.release();
        System.out.println("Persona " + id + " coge el ascensor " + idascensor);

        colaEntrada.release();
        // TODO Auto-generated method stub
        return idascensor;
    }

    @Override
    public void boardOffLift(int id, int liftId) throws InterruptedException {
        sc.acquire();
        ascensores.add(liftId);
        uso[liftId] += 1;
        if (hayEsperando) { // No hay disponibles, despierto si hay alguien esperando
            hayEsperando = false;
            esperaAscensor.release();
        }
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
