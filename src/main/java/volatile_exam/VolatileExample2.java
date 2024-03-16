package volatile_exam;

public class VolatileExample2 {
    private volatile int counter = 0;

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        VolatileExample2 volatileExample2 = new VolatileExample2();

        Thread writer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                volatileExample2.increment();
            }

            System.out.println("쓰기 쓰레드가 쓰기 작업을 마쳤습니다.");
        });

        Runnable reader = () -> {
            int localValue = -1;

            while (localValue < 1000) {
                localValue = volatileExample2.getCounter();

                System.out.println(Thread.currentThread().getName() + " 읽은 값: " + localValue);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        writer.start();

        for (int i = 0; i < 5; i++) {
            new Thread(reader).start();
        }
    }
}
