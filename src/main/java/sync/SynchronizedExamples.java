package sync;

/**
 * SynchronizedExamples의 모니터는 2개임
 * 클래스 모니터, 인스턴스 모니터
 * instanceMethod 와 instanceBlock 은 같은 객체라면 같은 모니터를 사용하고
 * staticMethod 와 staticBlock 은 같은 모니터를 사용함
 * <p>
 * 블록 동기화일 때는 모니터라면 인스턴스마다 각각의 모니터를 가지게 되기 때문에 각각 다른 모니터임
 * 그러나 메소드 동기화를 걸면 인스턴스가 생성되더라도 같은 모니터를 가지게 됨
 */
public class SynchronizedExamples {

    private int instanceCount = 0;

    private static int staticCount = 0;

    /**
     * 인스턴스의 메소드 전체를 동기화
     */
    public synchronized void instanceMethod() {
        instanceCount++;

        // 이것처럼 인스턴스의 모니터에서 클래스 변수를 수정하게 되면 클래스 모니터에서 얻은 동기화와 별개이기 때문에 값을 수정할 수 있음
        // 그니까 어떤 스레드에서 staticMethod 의 락을 얻어서 staticCount 를 수정했는데, 그때 동시에 instanceMethod 락을 얻은 스레드가 있다면
        // staticCount 가 동시에 수정될 수 있음
        // 그래서 인스턴스 모니터는 인스턴스끼리, 클래스 모니터는 클래스끼리 동기화할 수 있도록 조심해야 함
//        staticCount++;

        System.out.println("instance method sync: " + instanceCount);
    }

    /**
     * 클래스의 메소드 전체를 동기화
     */
    public static synchronized void staticMethod() {
        staticCount++;
        System.out.println("static method sync: " + staticCount);
    }

    /**
     * 인스턴스의 메소드 중 일부분을 동기화할 때
     */
    public void instanceBlock() {
        synchronized (this) {
            instanceCount++;
//            staticCount++;
            System.out.println("instance block sync: " + instanceCount);
        }
    }

    /**
     * 클래스의 메소드 중 일부분을 동기화할 때
     */
    public static void staticBlock() {
        synchronized (SynchronizedExamples.class) {
            staticCount++;
            System.out.println("static block sync: " + staticCount);
        }
    }

    public static void main(String[] args) {
        final var example = new SynchronizedExamples();

        new Thread(example::instanceMethod).start();
        new Thread(example::instanceBlock).start();
        new Thread(SynchronizedExamples::staticMethod).start();
        new Thread(SynchronizedExamples::staticBlock).start();
    }
}
