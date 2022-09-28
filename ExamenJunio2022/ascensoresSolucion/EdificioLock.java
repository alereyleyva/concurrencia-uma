package ascensoresSolucion;

import ascensores.Edificio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EdificioLock implements Edificio {
	private final int numAscensores;
	private List<Integer> ascensores;
	private int uso[];
	private Lock l = new ReentrantLock(true);
	private Condition colaEspera = l.newCondition();

	public EdificioLock(int numAs) {
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
		l.lock();
		int idas = -1;
		try {
			while (ascensores.size() == 0) {
				colaEspera.await();
			}
			idas = ascensores.get(0);
			ascensores.remove(0);
			System.out.println("Persona " + id + " coge el ascensor " + idas);

		} finally {
			l.unlock();
		}
		return idas;
	}

	@Override
	public void boardOffLift(int id, int liftId) throws InterruptedException {
		l.lock();
		try {
			ascensores.add(liftId);
			uso[liftId] += 1;
			System.out.println("Persona " + id + " sale del ascensor " + liftId);
			colaEspera.signal();

		} finally {
			l.unlock();
		}

	}

	@Override
	public void showUsage() {
		for (int i = 0; i < numAscensores; i++) {
			System.out.println("Ascensor " + i + " usado " + uso[i] + " veces");
		}

	}

}
