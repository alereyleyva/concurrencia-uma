package pajaritos;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos; // puede tener de 0 a B bichitos
	Semaphore comerBicho = new Semaphore(0, true);
	Semaphore echarBicho = new Semaphore(1, true);
	Semaphore mutex = new Semaphore(1, true);

	public void come(int id) throws InterruptedException {
		comerBicho.acquire();
		mutex.acquire();
		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		if (bichitos > 0)
			comerBicho.release();
		if (bichitos == B - 1)
			echarBicho.release();
		mutex.release();
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		echarBicho.acquire();
		mutex.acquire();
		bichitos++;
		if (bichitos == 1)
			comerBicho.release();
		if (bichitos < B)
			echarBicho.release();
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		mutex.release();
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
