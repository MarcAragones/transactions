package xyz.marcaragones.transactions.service.statistics;

import org.springframework.stereotype.Service;
import xyz.marcaragones.transactions.dto.StatisticsDTO;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public StatisticsDTO get() {
        return new StatisticsDTO();
    }
}
