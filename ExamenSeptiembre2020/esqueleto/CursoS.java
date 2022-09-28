package esqueleto;

import java.util.concurrent.Semaphore;

public class CursoS implements Curso {

	// Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;

	// Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;

	private Semaphore iniciacion = new Semaphore(1, true);
	private Semaphore mutex1 = new Semaphore(1, true);
	private Semaphore mutex2 = new Semaphore(1, true);
	private int alumnosIniciacion = 0;
	private int alumnosDentroAvanzado = 0;
	private int alumosConectadoAvanzado = 0;
	private int alumnosCursandoAvanzado = 0;
	private int alumnosFinalizadoAvanzado = 0;

	private Semaphore entrarAvanzado = new Semaphore(1, true);
	private Semaphore esperarAvanzado = new Semaphore(0, true);
	private Semaphore terminarAvanzado = new Semaphore(0, true);

	// El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de
	// iniciacion
	@Override
	public void esperaPlazaIniciacion(int id) throws InterruptedException {
		// Espera si ya hay 10 alumnos cursando esta parte
		iniciacion.acquire();

		mutex1.acquire();
		alumnosIniciacion++;
		// Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de
		// iniciacion
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");

		if (alumnosIniciacion < MAX_ALUMNOS_INI) {
			iniciacion.release();
		}
		mutex1.release();
	}

	// El alumno informa que ya ha terminado de cursar la parte de iniciacion
	@Override
	public void finIniciacion(int id) throws InterruptedException {

		mutex1.acquire();
		alumnosIniciacion--;
		mutex1.release();

		// Libera la conexion para que otro alumno pueda usarla
		iniciacion.release();

		// Mensaje a mostrar para indicar que el alumno ha terminado la parte de
		// principiantes
		System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");
	}

	/*
	 * El alumno tendra que esperar:
	 * - si ya hay un grupo realizando la parte avanzada
	 * - si todavia no estan los tres miembros del grupo conectados
	 */
	@Override
	public void esperaPlazaAvanzado(int id) throws InterruptedException {
		// Espera a que no haya otro grupo realizando esta parte
		entrarAvanzado.acquire();

		alumnosDentroAvanzado++;

		if (alumnosDentroAvanzado < ALUMNOS_AV)
			entrarAvanzado.release();

		alumosConectadoAvanzado++;

		// Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el
		// grupo
		System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");

		if (alumosConectadoAvanzado < ALUMNOS_AV) {
			// Espera a que haya tres alumnos conectados
			esperarAvanzado.acquire();
		}

		alumnosCursandoAvanzado++;

		if (alumnosCursandoAvanzado < ALUMNOS_AV) {
			esperarAvanzado.release();
		}

		// Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
		System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
	}

	/*
	 * El alumno:
	 * - informa que ya ha terminado de cursar la parte avanzada
	 * - espera hasta que los tres miembros del grupo hayan terminado su parte
	 */
	@Override
	public void finAvanzado(int id) throws InterruptedException {
		// Espera a que los 3 alumnos terminen su parte avanzada

		// Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del
		// grupo terminen
		System.out.println("PARTE AVANZADA: Alumno " + id + " termina su parte del proyecto. Espera al resto");

		alumnosFinalizadoAvanzado++;
		terminarAvanzado.acquire();

		// Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
		System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
	}
}
