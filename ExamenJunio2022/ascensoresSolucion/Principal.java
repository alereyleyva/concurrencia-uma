package ascensoresSolucion;

import ascensores.Edificio;
import ascensores.Persona;

public class Principal {
	public static final int NUM_LIFTS = 3;
	public static final int NUM_PERSONS = 20;

	public static void main(String[] args) throws InterruptedException {
		Edificio b = new EdificioLock(NUM_LIFTS); // Completar, e.g. new EdificioSemaforo(NUM_LIFTS); o new
													// EdificioSincronizado(NUM_LIFTS);
		Thread[] p = new Thread[NUM_PERSONS];
		for (int i = 0; i < NUM_PERSONS; i++) {
			p[i] = new Thread(new Persona(i, b));
			p[i].start();
		}

		// Escribe el código para mostrar el uso de los ascensores.
		for (int i = 0; i < NUM_PERSONS; i++)
			p[i].join();
		b.showUsage();
	}
}
