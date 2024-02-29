package thread_safety;

public class ThreadSafety {
//    public static void main(String[] args) throws InterruptedException {
//        final var thread1 = new Thread(() -> {
//            Member member = new Member("ryan", 10);
//            changeName(member);
//            System.out.println(Thread.currentThread().getName() + ": " + member.getName());
//        });
//
//        final var thread2 = new Thread(() -> {
//            Member member = new Member("orez", 10);
//            changeName(member);
//            System.out.println(Thread.currentThread().getName() + ": " + member.getName());
//        });
//
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//    }
//
//    public static void changeName(Member member) {
//        member.setName("other name");
//    }

//    public static void main(String[] args) throws InterruptedException {
//        final var sharedImmutableString = "Hello, World!";
//
//        final var thread1 = new Thread(() -> System.out.println(Thread.currentThread().getName() + ": " + sharedImmutableString));
//
//        final var thread2 = new Thread(() -> {
//            final var modifiedString = sharedImmutableString.toUpperCase();
//            System.out.println(Thread.currentThread().getName() + ": " + modifiedString);
//        });
//
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//
//        System.out.println("finished: " + sharedImmutableString);
//    }

//    private static void printNumber(int number) {
//        System.out.println(Thread.currentThread().getName() + ": " + ++number);
//    }
//
//    public static void main(String[] args) {
//        Thread thread1 = new Thread(() -> {
//            final var a = 1;
//            printNumber(a);
//            System.out.println(Thread.currentThread().getName() + " - after : " + a);
//
//        });
//
//        Thread thread2 = new Thread(() -> {
//            final var a = 2;
//            printNumber(a);
//            System.out.println(Thread.currentThread().getName() + " - after : " + a);
//        });
//
//        thread1.start();
//        thread2.start();
//    }

    public static void main(String[] args) throws InterruptedException {
        final var unsafeSequence = new UnsafeSequence(1);

        final var thread1 = new Thread(() -> {
            for (int i = 1; i < 10000; i++) {
                unsafeSequence.getNext();
            }
        });
        final var thread2 = new Thread(() -> {
            for (int i = 1; i < 10000; i++) {
                unsafeSequence.getNext();
            }
        });

        thread1.start();
        thread2.start();
    }
}