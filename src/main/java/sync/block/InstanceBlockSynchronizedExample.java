package sync.block;

/**
 * 이 예제는 동기화 이슈가 발생함
 * InstanceBlockSynchronizedExample 안에 this와 lock 모니터가 2개 존재하기 때문에
 * thread1, thread2는 같은 락을 가지고 thread3, thread4는 같은 락을 가짐. 즉, thread1과 thread3은 같은 락이 아님
 * 그래서 공유 자원인 count에 동시에 접근할 수 있게 됨
 */
public class InstanceBlockSynchronizedExample {

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
        final var counter = new InstanceBlockSynchronizedExample();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.thisBlockIncrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.thisBlockIncrement();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.lockBlockIncrement();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.lockBlockIncrement();
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

        System.out.println("최종 값: " + counter.getCount());
    }
}
