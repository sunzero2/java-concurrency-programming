package sync.method;

public class InstanceMethodSynchronizedExample2 {

    private int count = 0;

    public synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + "가 증가시킴: " + count);
    }

    public synchronized void decrement() {
        count--;
        System.out.println(Thread.currentThread().getName() + "가 감소시킴: " + count);
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final var counter1 = new InstanceMethodSynchronizedExample2();
        final var counter2 = new InstanceMethodSynchronizedExample2();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter1.increment();
                counter2.decrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter1.decrement();
                counter2.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("counter1의 최종 값: " + counter1.getCount());
        System.out.println("counter2의 최종 값: " + counter2.getCount());
    }
}
