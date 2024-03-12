package sync;

class BankAccount {
    private double balance;

    private final Object lock = new Object();

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        synchronized (lock) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        synchronized (lock) {
            if (balance < amount) {
                return false;
            }

            balance -= amount;
            return true;
        }
    }

    public boolean transfer(BankAccount to, double amount) {
        synchronized (this.lock) {
            if (this.withdraw(amount)) {
                synchronized (to.lock) {
                    to.deposit(amount);
                    return true;
                }
            }
        }

        return false;
    }

    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }
}

public class MultipleMonitorExample {

    public static void main(String[] args) throws InterruptedException {
        final var accountA = new BankAccount(1000);
        final var accountB = new BankAccount(1000);

        // accountA에서 accountB로 송금
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                final var result = accountA.transfer(accountB, 10);

                if (result) {
                    System.out.println("accountA -> accountB 10 송금 성공");
                } else {
                    System.out.println("accountA -> accountB 10 송금 실패");
                }
            }
        });

        // accountB에서 accountA로 송금
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                final var result = accountB.transfer(accountA, 10);

                if (result) {
                    System.out.println("accountB -> accountA 10 송금 성공");
                } else {
                    System.out.println("accountB -> accountA 10 송금 실패");
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("accountA 잔액: " + accountA.getBalance());
        System.out.println("accountB 잔액: " + accountB.getBalance());
    }
}
