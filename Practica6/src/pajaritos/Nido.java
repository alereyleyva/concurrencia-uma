package pajaritos;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de permisoComer
	private int bichitos = 0; // puede tener de 0 a B bichitos

	private Semaphore permisoDejarBicho = new Semaphore(1, true);
	private Semaphore permisoComer = new Semaphore(0, true);
	private Semaphore mutex = new Semaphore(1, true);

	public void come(int id) throws InterruptedException {
		permisoComer.acquire();
		mutex.acquire();
		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		if (bichitos > 0)
			permisoComer.release();
		if (bichitos == B - 1)
			permisoDejarBicho.release();
		mutex.release();
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		permisoDejarBicho.acquire();
		mutex.acquire();
		bichitos++;
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		if (bichitos == 1)
			permisoComer.release();
		if (bichitos < B)
			permisoDejarBicho.release();
		mutex.release();
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
