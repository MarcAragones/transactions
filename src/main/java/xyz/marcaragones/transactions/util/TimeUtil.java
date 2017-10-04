package xyz.marcaragones.transactions.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeUtil {

    private static final long ONE_MINUTE = 60 * 1000;

    public boolean isTimestampLessThanOneMinuteOld(Long timestamp) {
        long oneMinuteAgo = getOneMinuteAgo();
        return oneMinuteAgo < timestamp;
    }

    public long getOneMinuteAgo() {
        return System.currentTimeMillis() - ONE_MINUTE;
    }

    public long addOneMinute(long ts) {
        return ts + ONE_MINUTE;
    }

    public Date getDateFromMillis(long millis) {
        return new Date(millis);
    }
}
