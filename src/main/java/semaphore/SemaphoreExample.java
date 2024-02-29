package semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 1; i < 5; i++) {
            Thread thread = new Thread(new Worker(i, semaphore));
            thread.start();
        }
    }
}
