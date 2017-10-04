package xyz.marcaragones.transactions.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.marcaragones.transactions.dto.TransactionDTO;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;
import xyz.marcaragones.transactions.persistence.repository.StatisticsDAO;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;
import xyz.marcaragones.transactions.persistence.mapper.TransactionMapper;
import xyz.marcaragones.transactions.service.statistics.StatisticsUpdater;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    StatisticsUpdater statisticsUpdater;

    @Autowired
    TransactionRemover transactionRemover;

    @Autowired
    TransactionMapper mapper;

    @Override
    public void commit(TransactionDTO transactionDTO) {
        TransactionEntity entity = mapper.map(transactionDTO, TransactionEntity.class);
        saveTransaction(entity);
        scheduleTransactionExpiration(entity);
    }

    @Transactional
    private void saveTransaction(TransactionEntity entity) {
        transactionDAO.saveAndFlush(entity);
        statisticsUpdater.addTransaction(entity);
    }

    private void scheduleTransactionExpiration(TransactionEntity entity) {
        transactionRemover.scheduleTransactionExpiration(entity);
    }
}
