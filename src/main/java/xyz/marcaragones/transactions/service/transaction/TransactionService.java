package xyz.marcaragones.transactions.service.transaction;

import xyz.marcaragones.transactions.dto.TransactionDTO;

public interface TransactionService {

    void commit(TransactionDTO transactionDTO);
}
