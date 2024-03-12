package sync.method;

public class InstanceStaticMethodSynchronizedExamples {

    private static int staticCount = 0;

    private int instanceCount = 0;

    // InstanceStaticMethodSynchronizedExamples가 모니터가 됨
    public static synchronized void staticIncrement() {
        staticCount++;
        System.out.println(Thread.currentThread().getName() + "가 증가시킴: " + staticCount);
    }

    // this가 모니터가 됨
    public synchronized void instanceIncrement() {
        instanceCount++;
//        staticCount++;
        System.out.println(Thread.currentThread().getName() + "가 증가시킴: " + instanceCount);
    }

    public static int getStaticCount() {
        return staticCount;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public static void main(String[] args) throws InterruptedException {
        final var counter = new InstanceStaticMethodSynchronizedExamples();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.instanceIncrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.instanceIncrement();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                InstanceStaticMethodSynchronizedExamples.staticIncrement();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                InstanceStaticMethodSynchronizedExamples.staticIncrement();
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

        System.out.println("staticCount 최종 값: " + InstanceStaticMethodSynchronizedExamples.getStaticCount());
        System.out.println("instanceCount 최종 값: " + counter.getInstanceCount());
    }
}
