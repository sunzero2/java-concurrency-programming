package deadlock_exam;

public class NonDeadlockObjectsExample {

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
            synchronized (ResourceA.class) {
                System.out.println(Thread.currentThread().getName() + ": ResourceA methodA 부분 실행");

                synchronized (ResourceB.class) {
                    System.out.println(Thread.currentThread().getName() + ": ResourceB methodA 부분 실행");
                    resourceD.methodB2();
                }
            }
        }

        public void methodA2() {
            synchronized (ResourceA.class) {
                System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
            }
        }
    }

    static class ResourceB {
        public void methodB(ResourceA resourceC) {
            synchronized (ResourceA.class) {
                System.out.println(Thread.currentThread().getName() + ": ResourceA methodB 부분 실행");

                synchronized (ResourceB.class) {
                    System.out.println(Thread.currentThread().getName() + ": ResourceB methodB 부분 실행");
                    resourceC.methodA2();
                }
            }
        }

        public void methodB2() {
            synchronized (ResourceB.class) {
                System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
            }
        }
    }
}


