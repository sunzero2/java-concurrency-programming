package deadlock_exam;

public class DeadlockDynamicOrderExample {

    private static final Object lock1 = new Object();

    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> methodWithLocks(lock1, lock2));
        Thread thread2 = new Thread(() -> methodWithLocks(lock2, lock1));

        thread1.start();
        thread2.start();
    }

    private static void methodWithLocks(Object lockA, Object lockB) {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "이 " + lockA + "를 획득했습니다.");

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "이 " + lockB + "를 획득했습니다.");
            }
        }
    }
}
