package race_condition.compound_action;

import net.jcip.annotations.NotThreadSafe;

/**
 * 늦은 초기화에서 발생한 경쟁 조건. 이런 코드는 금물!
 */
@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            return new ExpensiveObject();
        }
        return instance;
    }
}
