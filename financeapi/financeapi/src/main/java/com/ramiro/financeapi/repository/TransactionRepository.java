package com.ramiro.financeapi.repository;

import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByCategory(TransactionCategory category);

    Page<Transaction> findByUser(User user, Pageable pageable);

    Page<Transaction> findByUserAndCreatedAtBetween(
            User user,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
        AND t.type = 'INCOME'
    """)
    BigDecimal getTotalIncome(User user);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
        AND t.type = 'EXPENSE'
    """)
    BigDecimal getTotalExpense(User user);

    long countByUser(User user);

    long countByUserAndType(User user, TransactionType type);
}