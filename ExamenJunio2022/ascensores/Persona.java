package ascensores;

import java.util.Random;

public class Persona implements Runnable {
	private static final Random rnd = new Random();

	private final int id;
	private final Edificio b;

	public Persona(int i, Edificio b) {
		this.id = i;
		this.b = b;
	}

	@Override
	public void run() {

		try {
			int liftId = b.boardOnLift(id);
			Thread.sleep(rnd.nextInt(500));
			b.boardOffLift(id, liftId);
		} catch (InterruptedException e) {

		}
	}
}
