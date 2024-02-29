package semaphore;

public class CountingSemaphore implements CommonSemaphore {
    private int signal;

    private int permits;

    public CountingSemaphore(int permits) {
        this.permits = permits;
        this.signal = permits;
    }

    @Override
    public synchronized void acquired() {
        while (signal == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " 락 대기중... 현재 semaphore 값: " + signal);
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.signal--;
        System.out.println(Thread.currentThread().getName() + " 락 획득, 현재 semaphore 값: " + signal);
    }

    @Override
    public synchronized void release() {
        if (this.signal < permits) {
            this.signal++;
            System.out.println(Thread.currentThread().getName() + " 락 해제, 현재 semaphore 값: " + signal);
            this.notify();
        }
    }
}
