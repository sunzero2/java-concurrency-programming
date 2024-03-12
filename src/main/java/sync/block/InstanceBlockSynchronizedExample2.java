package sync.block;

/**
 * 이 예제는 동기화 이슈가 발생함
 * thread1, thread2, thread3, thread4는 모두 다른 모니터 락을 사용함
 * 즉, 모든 스레드가 공유 자원인 count에 동시 접근이 가능함
 */
public class InstanceBlockSynchronizedExample2 {

    private int count = 0;

    private Object lock = new Object();

    public void thisBlockIncrement() {
        synchronized (this) {
            count++;
            System.out.println(Thread.currentThread().getName() + "가 this에 의해 증가시킴: " + count);
        }
    }

    public void lockBlockIncrement() {
        synchronized (lock) {
            count++;
            System.out.println(Thread.currentThread().getName() + "가 lock에 의해 증가시킴: " + count);
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final var counter1 = new InstanceBlockSynchronizedExample2();
        final var counter2 = new InstanceBlockSynchronizedExample2();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter1.thisBlockIncrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter2.thisBlockIncrement();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter1.lockBlockIncrement();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter2.lockBlockIncrement();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println("counter1의 최종 값: " + counter1.getCount());
        System.out.println("counter2의 최종 값: " + counter2.getCount());
    }
}
