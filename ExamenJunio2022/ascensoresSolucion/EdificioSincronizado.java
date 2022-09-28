package ascensoresSolucion;

import ascensores.Edificio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EdificioSincronizado implements Edificio {
	private final int numAscensores;
	private List<Integer> ascensores;
	private int uso[];

	public EdificioSincronizado(int numAs) {
		this.numAscensores = numAs;
		ascensores = new LinkedList<Integer>();
		uso = new int[numAscensores];
		for (int i = 0; i < numAscensores; i++) {
			ascensores.add(i);
			uso[i] = 0;
		}
	}

	@Override
	public synchronized int boardOnLift(int id) throws InterruptedException {
		while (ascensores.size() == 0) {
			wait();
		}
		int idas = ascensores.get(0);
		ascensores.remove(0);
		System.out.println("Persona " + id + " coge el ascensor " + idas);
		return idas;
	}

	@Override
	public synchronized void boardOffLift(int id, int liftId) throws InterruptedException {
		ascensores.add(liftId);
		uso[liftId] += 1;
		System.out.println("Persona " + id + " sale del ascensor " + liftId);
		notify();
	}

	@Override
	public void showUsage() {
		for (int i = 0; i < numAscensores; i++) {
			System.out.println("Ascensor " + i + " usado " + uso[i] + " veces");
		}

	}

}
