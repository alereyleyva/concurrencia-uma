public class Fibonacci extends Thread {
    private int n;
    private int value;
    private int previousValue;

    public Fibonacci(int n) {
        this.n = n;
    }

    public int getValue() {
        return value;
    }

    public int getPreviousValue() {
        return previousValue;
    }

    @Override
    public void run() {
        if (n == 0)
            value = 0;
        else if (n == 1) {
            value = 1;
            previousValue = 0;
        } else {
            Fibonacci fibonacci = new Fibonacci(n - 1);

            fibonacci.start();

            try {
                fibonacci.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            value = fibonacci.getValue() + fibonacci.getPreviousValue();
            previousValue = fibonacci.getValue();
        }
    }
}
