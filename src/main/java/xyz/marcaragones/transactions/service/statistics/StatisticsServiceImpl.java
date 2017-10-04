package xyz.marcaragones.transactions.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.entity.StatisticsEntity;
import xyz.marcaragones.transactions.persistence.mapper.StatisticsMapper;
import xyz.marcaragones.transactions.persistence.repository.StatisticsDAO;

import org.springframework.transaction.annotation.Transactional;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsDAO statisticsDAO;

    @Autowired
    StatisticsMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public StatisticsDTO get() {
        StatisticsEntity entity = statisticsDAO.findAll().get(0);
        StatisticsDTO statisticsDTO = mapper.map(entity, StatisticsDTO.class);
        statisticsDTO.setAvg(entity.getAvg());

        return statisticsDTO;
    }
}
