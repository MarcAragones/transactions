package xyz.marcaragones.transactions.persistence.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.entity.StatisticsEntity;

@Component
public class StatisticsMapper extends DozerBeanMapper {

    public StatisticsMapper() {
        this.addMapping(getBeanMappingBuilder());
    }

    private BeanMappingBuilder getBeanMappingBuilder() {

        return new BeanMappingBuilder() {
            protected void configure() {
                mapping(StatisticsEntity.class, StatisticsDTO.class)
                        .fields("sum", "sum")
                        .fields("max", "max")
                        .fields("min", "min")
                        .fields("count", "count");
            }
        };
    }
}
