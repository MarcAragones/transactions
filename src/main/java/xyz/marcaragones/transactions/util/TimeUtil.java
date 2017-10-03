package xyz.marcaragones.transactions.util;

import org.springframework.stereotype.Component;

@Component
public class TimeUtil {

    private static final long ONE_MINUTE = 60 * 1000;

    public boolean isTimestampLessThanOneMinuteOld(Long timestamp) {
        long oneMinuteAgo = System.currentTimeMillis() - ONE_MINUTE;
        return oneMinuteAgo < timestamp;
    }
}
