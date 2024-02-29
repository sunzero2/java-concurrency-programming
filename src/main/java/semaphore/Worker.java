package semaphore;

import java.util.concurrent.Semaphore;

public record Worker(int id, Semaphore semaphore) implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Worker" + id + " is trying to acquire a permit.");
            semaphore.acquire();
            System.out.println("Worker" + id + " has acquired a permit.");

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Worker" + id + " is releasing the permit.");
            semaphore.release();
            System.out.println("Worker" + id + " has released the permit.");
        }
    }
}
