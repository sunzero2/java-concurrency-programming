package thread_safety;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    public UnsafeSequence(int initialValue) {
        this.value = initialValue;
    }

    // 유일한 값을 리턴
    public int getNext() {
        return value++;
    }

    public int getValue() {
        return value;
    }
}

