package xyz.marcaragones.transactions.service.transaction;

import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

public interface TransactionRemover {

    void scheduleTransactionExpiration(TransactionEntity transactionEntity);
}
