package deadlock_exam;

public class DeadlockObjectsExample {

    public static void main(String[] args) {
        final var resourceA = new ResourceA();
        final var resourceB = new ResourceB();

        Thread thread1 = new Thread(() -> resourceA.methodA(resourceB));
        Thread thread2 = new Thread(() -> resourceB.methodB(resourceA));

        thread1.start();
        thread2.start();
    }

    static class ResourceA {
        public synchronized void methodA(ResourceB resourceB) {
            System.out.println(Thread.currentThread().getName() + ": methodA 실행");

            try {
                // 각 메소드에 지연을 추가해 데드락 가능성 높임
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            resourceB.methodB2();
        }

        public synchronized void methodA2() {
            System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
        }
    }

    static class ResourceB {
        public synchronized void methodB(ResourceA resourceA) {
            System.out.println(Thread.currentThread().getName() + ": methodB 실행");

            try {
                // 각 메소드에 지연을 추가해 데드락 가능성 높임
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            resourceA.methodA2();
        }

        public synchronized void methodB2() {
            System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
        }
    }
}


