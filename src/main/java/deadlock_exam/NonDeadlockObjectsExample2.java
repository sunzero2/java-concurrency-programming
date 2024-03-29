package deadlock_exam;

public class NonDeadlockObjectsExample2 {

    public static void main(String[] args) {
        final var resourceA = new ResourceA();
        final var resourceB = new ResourceB();

        Thread thread1 = new Thread(() -> resourceA.methodA(resourceB));
        Thread thread2 = new Thread(() -> resourceB.methodB(resourceA));

        thread1.start();
        thread2.start();
    }

    static class ResourceA {
        public void methodA(ResourceB resourceD) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ": methodA 부분 실행");
            }
            resourceD.methodB2();
        }

        public synchronized void methodA2() {
            System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
        }
    }

    static class ResourceB {
        public void methodB(ResourceA resourceC) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ": methodB 부분 실행");
            }
            resourceC.methodA2();
        }

        public synchronized void methodB2() {
            System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
        }
    }
}


