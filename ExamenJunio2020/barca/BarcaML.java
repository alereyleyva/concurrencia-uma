import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarcaML implements Barca {

    private Lock l = new ReentrantLock(true);
    private int orilla = 1;

    @Override
    public void subir(int id, int pos) throws InterruptedException {
    }

    @Override
    public int bajar(int id) throws InterruptedException {
        return 0;
    }

    @Override
    public void esperoSuban() throws InterruptedException {
    }

    @Override
    public void finViaje() throws InterruptedException {
    }

}
