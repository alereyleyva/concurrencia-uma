package esqueleto;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CursoML implements Curso {

	// Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;

	// Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;

	private Lock l = new ReentrantLock(true);

	private Condition iniciacion = l.newCondition();
	private Condition entrarAvanzado = l.newCondition();
	private Condition esperandoAvanzado = l.newCondition();
	private Condition finAvanzado = l.newCondition();

	private int alumnosCursandoIniciacion = 0, alumnosCursandoAvanzado = 0, alumnosTerminaAvanzado = 0;

	@Override
	public void esperaPlazaIniciacion(int id) throws InterruptedException {
		l.lock();
		try {
			while (alumnosCursandoIniciacion == MAX_ALUMNOS_INI) {
				// Espera si ya hay 10 alumnos cursando esta parte
				iniciacion.await();
			}

			alumnosCursandoIniciacion++;

			// Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de
			// iniciacion
			System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");
		} finally {
			l.unlock();
		}
	}

	@Override
	public void finIniciacion(int id) throws InterruptedException {
		l.lock();
		try {

			// Mensaje a mostrar para indicar que el alumno ha terminado la parte de
			// principiantes
			System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");

			// Libera la conexion para que otro alumno pueda usarla
			alumnosCursandoIniciacion--;
			iniciacion.signal();

		} finally {
			l.unlock();
		}
	}

	@Override
	public void esperaPlazaAvanzado(int id) throws InterruptedException {
		l.lock();
		try {
			// Espera a que no haya otro grupo realizando esta parte
			while (alumnosCursandoAvanzado == ALUMNOS_AV) {
				entrarAvanzado.await();
			}

			alumnosCursandoAvanzado++;

			// Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el
			// grupo
			System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");

			// Espera a que haya tres alumnos conectados
			while (alumnosCursandoAvanzado != ALUMNOS_AV) {
				esperandoAvanzado.await();
			}

			// Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
			System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");

			esperandoAvanzado.signal();
		} finally {
			l.unlock();
		}
	}

	@Override
	public void finAvanzado(int id) throws InterruptedException {
		l.lock();
		try {
			alumnosTerminaAvanzado++;
			// Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del
			// grupo terminen
			System.out.println("PARTE AVANZADA: Alumno " + id + " termina su parte del proyecto. Espera al resto");

			while (alumnosTerminaAvanzado != ALUMNOS_AV) {
				// Espera a que los 3 alumnos terminen su parte avanzada
				finAvanzado.await();
			}

			alumnosCursandoAvanzado--;
			finAvanzado.signal();

			if (alumnosCursandoAvanzado == 0) {
				// Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
				System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
				alumnosTerminaAvanzado = 0;
				for (int i = 0; i < ALUMNOS_AV; i++) {
					entrarAvanzado.signal();
				}
			}
		} finally {
			l.unlock();
		}
	}

}
