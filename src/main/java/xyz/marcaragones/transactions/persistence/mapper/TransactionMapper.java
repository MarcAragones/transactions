package xyz.marcaragones.transactions.persistence.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import xyz.marcaragones.transactions.dto.TransactionDTO;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

@Component
public class TransactionMapper extends DozerBeanMapper {

    public TransactionMapper() {
        this.addMapping(getBeanMappingBuilder());
    }

    private BeanMappingBuilder getBeanMappingBuilder() {

        return new BeanMappingBuilder() {
            protected void configure() {
                mapping(TransactionDTO.class, TransactionEntity.class)
                        .fields("amount", "amount")
                        .fields("timestamp", "timestamp");
            }
        };
    }
}
