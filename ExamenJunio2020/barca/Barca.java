
public interface Barca {

	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public void subir(int id, int pos) throws InterruptedException;

	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public int bajar(int id) throws InterruptedException;

	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public void esperoSuban() throws InterruptedException;

	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que
	 * bajarse
	 */
	public void finViaje() throws InterruptedException;
}
