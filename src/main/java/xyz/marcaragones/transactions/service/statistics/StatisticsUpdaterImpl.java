package xyz.marcaragones.transactions.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.marcaragones.transactions.persistence.entity.StatisticsEntity;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;
import xyz.marcaragones.transactions.persistence.repository.StatisticsDAO;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;
import xyz.marcaragones.transactions.util.TimeUtil;

import java.util.List;

@Component
public class StatisticsUpdaterImpl implements StatisticsUpdater {

    @Autowired
    StatisticsDAO statisticsDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    TimeUtil timeUtil;

    @Override
    @Transactional
    public void addTransaction(TransactionEntity transaction) {
        StatisticsEntity statistics = statisticsDAO.findAll().get(0);
        statistics.addAmount(transaction.getAmount());
        statisticsDAO.saveAndFlush(statistics);
    }

    @Override
    @Transactional
    public void removeTransaction(TransactionEntity transaction) {
        StatisticsEntity statistics = statisticsDAO.findAll().get(0);
        statistics.removeAmount(transaction.getAmount());
        statistics.setMax(calculateNewMax());
        statistics.setMin(calculateNewMin());
        statisticsDAO.saveAndFlush(statistics);
    }

    @Transactional(readOnly = true)
    private double calculateNewMax() {
        return transactionDAO.findMaxAmountSinceTimestamp(timeUtil.getOneMinuteAgo());
    }

    @Transactional(readOnly = true)
    private double calculateNewMin() {
        return transactionDAO.findMinAmountSinceTimestamp(timeUtil.getOneMinuteAgo());
    }
}
