package deadlock_exam;

public class DeadlockOrderExample {

    private static final Object lock1 = new Object();

    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(DeadlockOrderExample::process1);
        Thread thread2 = new Thread(DeadlockOrderExample::process2);

        thread1.start();
        thread2.start();
    }

    private static void process1() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + "이 lock1을 획득했습니다.");

            try {
                // 스레드 간 경쟁 조건을 만들기 위해 잠시 대기
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "이 lock2를 획득했습니다.");
            }
        }
    }

    private static void process2() {
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + "이 lock2를 획득했습니다.");

            try {
                // 스레드 간 경쟁 조건을 만들기 위해 잠시 대기
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "이 lock1을 획득했습니다.");
            }
        }
    }
}
