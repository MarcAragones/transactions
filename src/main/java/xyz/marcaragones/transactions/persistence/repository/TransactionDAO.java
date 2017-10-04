package xyz.marcaragones.transactions.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.marcaragones.transactions.persistence.entity.TransactionEntity;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionEntity, Long> {

    TransactionEntity findFirstByOrderByAmountDesc();
}
