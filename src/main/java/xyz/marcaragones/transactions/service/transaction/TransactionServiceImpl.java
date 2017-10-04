package xyz.marcaragones.transactions.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.marcaragones.transactions.dto.TransactionDTO;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;
import xyz.marcaragones.transactions.persistence.mapper.TransactionMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    TransactionMapper mapper;

    @Override
    public void commit(TransactionDTO transactionDTO) {
        TransactionEntity entity = mapper.map(transactionDTO, TransactionEntity.class);
        saveTransaction(entity);
    }

    @Transactional
    private void saveTransaction(TransactionEntity entity) {
        transactionDAO.save(entity);
    }
}
