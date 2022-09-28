package ascensores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EdificioS implements Edificio {

    private Semaphore lift = new Semaphore(1, true);
    
    private List<Integer> liftsUsage;
    private List<Integer> availableLifts;
    private Semaphore liftMutex = new Semaphore(1, true);

    public EdificioS(int numLifts) {
        this.availableLifts = new ArrayList<>();
        this.liftsUsage = new ArrayList<>();
        for (int i = 0; i < numLifts; i++) {
            availableLifts.add(i);
            liftsUsage.add(0);
        }
    }

    @Override
    public int boardOnLift(int id) throws InterruptedException {

        lift.acquire();

        liftMutex.acquire();

        Integer liftId = availableLifts.get(0);
        availableLifts.remove(0);

        System.out.println("Persona " + id + " coge el ascensor " + liftId);

        if (availableLifts.size() > 0) {
            lift.release();
        }

        liftMutex.release();

        return liftId;
    }

    @Override
    public void boardOffLift(int id, int liftId) throws InterruptedException {

        liftMutex.acquire();

        Integer previousUsage = liftsUsage.get(liftId);
        liftsUsage.set(liftId, previousUsage + 1);

        availableLifts.add(liftId);

        System.out.println("Persona " + id + " sale del ascensor " + liftId);

        if (availableLifts.size() == 1) lift.release();

        liftMutex.release();


    }

    @Override
    public void showUsage() {
        for (int id = 0; id < liftsUsage.size(); id++) {
            System.out.println("Ascensor " + id + " usado " + liftsUsage.get(id) + " veces");
        }
    }
    
}
