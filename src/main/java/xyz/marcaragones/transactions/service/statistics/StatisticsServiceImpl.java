package xyz.marcaragones.transactions.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;
import xyz.marcaragones.transactions.util.TimeUtil;

import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    TimeUtil timeUtil;

    @Override
    public StatisticsDTO get() {
        long oneMinuteAgo = timeUtil.getOneMinuteAgo();

        return getStatisticsSinceTimestamp(oneMinuteAgo);
    }

    @Transactional(readOnly = true)
    private StatisticsDTO getStatisticsSinceTimestamp(long ts) {
        return transactionDAO.findStatisticsSinceTimestamp(ts);
    }
}
