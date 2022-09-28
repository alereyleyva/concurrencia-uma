
public class Presa implements Runnable {
	private int id;
	private Lago l;
	private int drenar;

	public Presa(int id, Lago l, int drenar) {
		this.id = id;
		this.l = l;
		this.drenar = drenar;
	}

	public void run() {
		for (int i = 0; i < drenar; i++) {
			l.pedirTurno(id);
			l.esperarTurno(id);
			while (l.nivel() == 0) {
				System.out.println("Presa " + id + " intenta disminuir con nivel " + l.nivel());
				l.cederTurno(id);
				l.esperarTurno(id);
			}
			if (id == 2)
				l.decPresa0();
			else
				l.decPresa1();
			l.terminarTurno(id);
		}
	}
}
