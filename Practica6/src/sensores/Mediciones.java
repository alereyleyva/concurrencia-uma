package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {

    private Semaphore trabajador = new Semaphore(0, true);

    private Semaphore[] sensores = new Semaphore[3];

    private Semaphore mutex = new Semaphore(1, true);
    private int medicionesRecogidas = 0;

    public Mediciones() {
        for (int i = 0; i < sensores.length; i++) {
            sensores[i] = new Semaphore(1, true);
        }
    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        sensores[id].acquire();
        mutex.acquire();
        medicionesRecogidas++;
        if (medicionesRecogidas == 3)
            trabajador.release();
        mutex.release();
        System.out.println("Sensor " + id + " deja sus mediciones.");
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
        medicionesRecogidas = 0;
        mutex.release();
        for (int i = 0; i < sensores.length; i++) {
            sensores[i].release();
        }
        System.out.println("El trabajador ha terminado sus tareas");
    }

}
