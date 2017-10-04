package xyz.marcaragones.transactions.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        transactionDAO.save(entity);
    }
}
