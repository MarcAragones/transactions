package xyz.marcaragones.transactions.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.entity.StatisticsEntity;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

@Repository
public interface StatisticsDAO extends JpaRepository<StatisticsEntity, Long> {

}
