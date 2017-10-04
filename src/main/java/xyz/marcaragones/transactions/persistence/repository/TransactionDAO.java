package xyz.marcaragones.transactions.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT new xyz.marcaragones.transactions.dto.StatisticsDTO(" +
            "COALESCE(SUM(amount), 0)" +
            ", COALESCE(AVG(amount), 0)" +
            ", COALESCE(MAX(amount), 0)" +
            ", COALESCE(MIN(amount), 0)" +
            ", COALESCE(COUNT(amount), 0)" +
            ")" +
            " FROM TransactionEntity WHERE timestamp > :timestamp")
    StatisticsDTO findStatisticsSinceTimestamp(@Param("timestamp") long timestamp);

    @Query("SELECT COALESCE(MAX(amount), 0) FROM TransactionEntity WHERE timestamp > :timestamp")
    double findMaxAmountSinceTimestamp(@Param("timestamp") long timestamp);

    @Query("SELECT COALESCE(MIN(amount), 1.7976931348623157E+308) FROM TransactionEntity WHERE timestamp > :timestamp")
    double findMinAmountSinceTimestamp(@Param("timestamp") long timestamp);
}
