package race_condition.lock;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class CachedFactorizer {
    @GuardedBy("this")
    private BigInteger lastNumber;

    @GuardedBy("this")
    private BigInteger[] lastFactors;

    /**
     * 여기서는 다시 long 필드를 쓴다. AtomicLong 클래스를 써도 괜찮지만 별다른 장점이 없다.
     * 여기에서는 이미 synchronized 블록을 사용해 동기화를 하고 있다.
     * 두 가지 동기화 수단을 사용해 봐야 혼동을 줄 뿐 성능, 안정성 측면에서 이점이 없다.
     */
    @GuardedBy("this")
    private long hits;

    @GuardedBy("this")
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheGitRatio() {
        return (double) cacheHits / (double) hits;
    }

    /**
     * 최근 입력 값과 그 결과를 캐시하는 서블릿
     * synchronized 블록 밖에 있는 코드는 다른 스레드와 공유되지 않는 지역 (스택 상의) 변수만 사용하기 때문에 동기화가 필요 없다.
     */
    public void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;

        // 캐시된 결과를 갖고 있는지 검사하는 일종의 확인 후 동작(check-and-act) 부분
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            // 오래 걸릴 가능성이 있는 인수분해 작업 전에 락을 놓는다!
            factors = factor(i);

            // 캐시된 입력 값과 결과를 새로운 값으로 변경하는 부분
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }

        encodeIntoResponse(res, factors);
    }

    private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {

    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }


    private BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.valueOf(3);
    }
}
