package thread_safety;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeSequence {
    private final AtomicInteger value;

    public SafeSequence(int initialValue) {
        this.value = new AtomicInteger(initialValue);
    }

    // 유일한 값을 리턴
    public int getNext() {
        return value.getAndIncrement();
    }

    public static void main(String[] args) {
        SafeSequence safeSequence = new SafeSequence(0);

        for (int i = 0; i < 4; i++) {
            new Thread(() -> System.out.println(Thread.currentThread().getName() + ": " + safeSequence.getNext())).start();
        }
    }
}
