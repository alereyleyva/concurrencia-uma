package lago;

public class Lago {
	private volatile int nivel = 0;
	private volatile boolean r0 = false;
	private volatile boolean r1 = false;
	private volatile boolean p0 = false;
	private volatile boolean p1 = false;

	private volatile int rioTurno = 0;
	private volatile int presaTurno = 0;

	// 0 -> Rio && 1 -> Presa
	private volatile int finalTurno = 0;
	private volatile boolean rio = false;
	private volatile boolean presa = false;

	public Lago(int valorInicial) {
		nivel = valorInicial;
	}

	// f0IncDec, f0Inc
	public void incRio0() {
		r0 = true;
		rioTurno = 1;
		while (r1 && rioTurno == 1)
			Thread.yield();

		rio = true;
		finalTurno = 1;
		while (presa && finalTurno == 1)
			Thread.yield();

		nivel++;

		rio = false;
		r0 = false;
	}

	// f0IncDec, f1Inc
	public void incRio1() {
		r1 = true;
		rioTurno = 0;
		while (r0 && rioTurno == 0)
			Thread.yield();

		rio = true;
		finalTurno = 1;
		while (presa && finalTurno == 1)
			Thread.yield();

		nivel++;

		rio = false;
		r1 = false;
	}

	// f1IncDec, f0Dec
	public void decPresa0() {
		p0 = true;
		presaTurno = 1;
		while (p1 && presaTurno == 1)
			Thread.yield();

		while (nivel == 0)
			Thread.yield();

		presa = true;
		finalTurno = 0;

		while (rio && finalTurno == 0)
			Thread.yield();

		nivel--;

		presa = false;
		p0 = false;
	}

	// f1IncDec, f1Dec
	public void decPresa1() {
		p1 = true;
		presaTurno = 0;
		while (p0 && presaTurno == 0)
			Thread.yield();

		while (nivel == 0)
			Thread.yield();

		presa = true;
		finalTurno = 0;

		while (rio && finalTurno == 0)
			Thread.yield();

		nivel--;

		presa = false;
		p1 = false;
	}

	public int nivel() {
		return nivel;
	}
}
