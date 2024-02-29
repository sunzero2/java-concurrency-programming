package race_condition.compound_action;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import net.jcip.annotations.NotThreadSafe;

import java.io.IOException;
import java.math.BigInteger;

@NotThreadSafe
public class UnsafeCountingFactorizer {
    private long count = 0;

    public long getCount() {
        return count;
    }

    /**
     * 동기화 구문 없이 요청 횟수를 세는 서블릿. 이런 코드는 금물!
     */
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
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
