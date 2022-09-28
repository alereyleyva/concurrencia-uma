
public class Lago {
	private volatile int[] turnos;
	private volatile boolean[] pidiendoTurno;
	private volatile int nivel;

	public Lago(int valorInicial) {
		nivel = valorInicial;
		turnos = new int[4];
		pidiendoTurno = new boolean[4];
	}

	public void pedirTurno(int id) {
		pidiendoTurno[id] = true;
		int max = ultimoTurno(turnos);
		turnos[id] = max + 1;
		pidiendoTurno[id] = false;
	}

	private int ultimoTurno(int[] turnos) {
		int max = 0;
		for (int i = 0; i < turnos.length; i++) {
			if (max < turnos[i])
				max = turnos[i];
		}
		return max;
	}

	public void esperarTurno(int id) {
		for (int i = 0; i < turnos.length; i++) {
			while (pidiendoTurno[i])
				Thread.yield();
			while (!meToca(id, i))
				Thread.yield();
		}
	}

	private boolean meToca(int id, int i) {
		if (turnos[i] > 0 && turnos[i] < turnos[id])
			return false;
		else if (turnos[i] == turnos[id] && i < id)
			return false;
		else
			return true;
	}

	public void cederTurno(int id) {
		turnos[id]++;
	}

	public void terminarTurno(int id) {
		turnos[id] = 0;
	}

	// f0IncDec, f0Inc
	public void incRio0() {
		nivel++;
		// System.out.println("Incremento Rio 0 -> Nivel " + nivel);
	}

	// f0IncDec, f1Inc
	public void incRio1() {
		nivel++;
		// System.out.println("Incremento Rio 1 -> Nivel " + nivel);
	}

	// f1IncDec, f0Dec
	public void decPresa0() {
		nivel--;
		// System.out.println("Disminución Presa 0 -> Nivel " + nivel);
	}

	// f1IncDec, f1Dec
	public void decPresa1() {
		nivel--;
		// System.out.println("Disminución Presa 1 -> Nivel " + nivel);
	}

	public int nivel() {
		return nivel;
	}
}
