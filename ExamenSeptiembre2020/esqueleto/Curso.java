package esqueleto;

public interface Curso {

    public void esperaPlazaIniciacion(int id) throws InterruptedException;

    public void finIniciacion(int id) throws InterruptedException;

    public void esperaPlazaAvanzado(int id) throws InterruptedException;

    public void finAvanzado(int id) throws InterruptedException;

}
