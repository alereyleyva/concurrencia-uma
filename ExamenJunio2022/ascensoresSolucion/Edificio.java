package ascensoresSolucion;

public interface Edificio {

	int boardOnLift(int id) throws InterruptedException;

	void boardOffLift(int id, int liftId) throws InterruptedException;

	void showUsage();

}
