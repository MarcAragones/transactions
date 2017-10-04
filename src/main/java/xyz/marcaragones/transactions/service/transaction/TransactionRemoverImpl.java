package xyz.marcaragones.transactions.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;
import xyz.marcaragones.transactions.service.statistics.StatisticsUpdater;
import xyz.marcaragones.transactions.util.TimeUtil;

import java.util.Date;

@Component
public class TransactionRemoverImpl implements TransactionRemover {

    @Autowired
    TaskScheduler scheduler;

    @Autowired
    StatisticsUpdater statisticsUpdater;

    @Autowired
    TimeUtil timeUtil;

    @Override
    public void scheduleTransactionExpiration(TransactionEntity transactionEntity) {
        long timestamp = timeUtil.addOneMinute(transactionEntity.getTimestamp());
        Date startTime = timeUtil.getDateFromMillis(timestamp);

        scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                statisticsUpdater.removeTransaction(transactionEntity);
            }
        }, startTime);
    }
}
