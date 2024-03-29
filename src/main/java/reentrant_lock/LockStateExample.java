package reentrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStateExample {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("락 1번 획득");
            lock.lock();

            try {
                System.out.println("락 2번 획득");
                lock.lock();

                try {
                    System.out.println("락 3번 획득");
                    lock.lock();
                } finally {
                    System.out.println("락 1번 해제");
                    lock.unlock();
                }
            } finally {
                System.out.println("락 2번 해제");
                lock.unlock();
            }

            System.out.println("락 3번 해제");
            lock.unlock();
        }).start();

        new Thread(() -> {
            lock.lock();

            try {
                System.out.println("스레드2 락 획득");
            } finally {
                System.out.println("스레드2 락 해제");
                lock.unlock();
            }
        }).start();
    }
}
