package sync.block;

class MethodBlock {

}

public class StaticBlockSynchronizedExample {

    private static int count = 0;

    public static void thisBlockIncrement() {
        synchronized (StaticBlockSynchronizedExample.class) {
            count++;
            System.out.println(Thread.currentThread().getName() + "가 this에 의해 증가시킴: " + count);
        }
    }

    public static void lockBlockIncrement() {
        synchronized (MethodBlock.class) {
            count++;
            System.out.println(Thread.currentThread().getName() + "가 lock에 의해 증가시킴: " + count);
        }
    }

    public static int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                StaticBlockSynchronizedExample.thisBlockIncrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                StaticBlockSynchronizedExample.lockBlockIncrement();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("최종 값: " + StaticBlockSynchronizedExample.getCount());
    }
}
