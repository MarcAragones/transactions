package xyz.marcaragones.transactions.service.statistics;

import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

public interface StatisticsUpdater {

    void addTransaction(TransactionEntity transaction);

    void removeTransaction(TransactionEntity transaction);
}
