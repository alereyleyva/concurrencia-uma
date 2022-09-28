package ascensores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EdificioML implements Edificio {

    private Lock l = new ReentrantLock(true);

    private Condition enterLift = l.newCondition();

    private List<Integer> liftsUsage;
    private List<Integer> availableLifts;

    public EdificioML(int numLifts) {
        this.availableLifts = new ArrayList<>();
        for (int i = 0; i < numLifts; i++) {
            availableLifts.add(i);
        }
        this.liftsUsage = new ArrayList<>();
        for (int i = 0; i < numLifts; i++) {
            liftsUsage.add(0);
        }
    }

    @Override
    public int boardOnLift(int id) throws InterruptedException {
        l.lock();
        try {

            while (availableLifts.size() == 0) {
                enterLift.await();
            }

            Integer liftId = availableLifts.get(0);
            availableLifts.remove(0);

            Integer previousUsage = liftsUsage.get(liftId);
            liftsUsage.set(liftId, previousUsage + 1);

            System.out.println("Persona " + id + " coge el ascensor " + liftId);
            return liftId;
        } finally {
            l.unlock();
        }
    }

    @Override
    public void boardOffLift(int id, int liftId) throws InterruptedException {
        l.lock();
        try {

            availableLifts.add(liftId);

            enterLift.signal();

            System.out.println("Persona " + id + " sale del ascensor " + liftId);
        } finally {
            l.unlock();
        }
    }

    @Override
    public void showUsage() {
        for (int id = 0; id < liftsUsage.size(); id++) {
            System.out.println("Ascensor " + id + " usado " + liftsUsage.get(id) + " veces");
        }
    }
    
}
