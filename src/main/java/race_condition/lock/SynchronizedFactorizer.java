package race_condition.lock;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class SynchronizedFactorizer {
    @GuardedBy("this")
    private BigInteger lastNumber;

    @GuardedBy("this")
    private BigInteger[] lastFactors;

    /**
     * 마지막 결과를 캐시하지만 성능이 현저하게 떨어지는 서블릿. 이런 코드는 금물!
     * 인수분해 서블릿을 여러 클라이언트가 동시에 사용할 수 없고, 이 때문에 응답성이 엄청나게 떨어질 수 있음.
     */
    public synchronized void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);

        if (i.equals(lastNumber)) {
            encodeIntoResponse(res, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(res, factors);
        }
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
