public class FibonacciMain {

    public static void main(String[] args) {
        Fibonacci fib = new Fibonacci(10);

        fib.start();

        try {
            fib.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(fib.getValue());
    }
}
