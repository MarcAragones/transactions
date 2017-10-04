package xyz.marcaragones.transactions.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    TransactionDAO transactionDAO;

    @Override
    public StatisticsDTO get() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        TransactionEntity max = transactionDAO.findFirstByOrderByAmountDesc();

        if (max != null) {
            statisticsDTO.setMax(max.getAmount());
        }

        return statisticsDTO;
    }
}
