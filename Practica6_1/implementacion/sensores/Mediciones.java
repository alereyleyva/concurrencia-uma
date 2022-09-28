package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {

    private Semaphore[] semaforos;
    private Semaphore trabajador;
    private Semaphore mutex;
    private int n;

    public Mediciones() {
        this.semaforos = new Semaphore[3];
        for (int i = 0; i < semaforos.length; i++) {
            semaforos[i] = new Semaphore(0, true);
        }
        this.mutex = new Semaphore(1, true);
        this.trabajador = new Semaphore(0, true);
    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        mutex.acquire();
        n++;
        System.out.println("Sensor " + id + " deja sus mediciones.");
        if (n == 3)
            trabajador.release();
        mutex.release();
        semaforos[id].acquire();
    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        trabajador.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");

    }

    /**
     * El trabajador indica que ha terminado sus tareas
     * 
     * @throws InterruptedException
     */
    public void finTareas() throws InterruptedException {
        mutex.acquire();
        n = 0;
        mutex.release();
        for (int i = 0; i < semaforos.length; i++) {
            semaforos[i].release();
        }
        System.out.println("El trabajador ha terminado sus tareas");

    }

}
