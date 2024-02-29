package thread_safety;

public class SyncExample {
    private static int count = 0;

    private static final int ITERATIONS = 100000;

    public static void main(String[] args) throws InterruptedException {
        final var thread1 = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                synchronized (SyncExample.class) {
                    count++;
                }
            }
        });

        final var thread2 = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                synchronized (SyncExample.class) {
                    count++;
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("expected results: " + (2 * ITERATIONS));
        System.out.println("actual results: " + count);
    }
}
