package com.ramiro.financeapi.repository;

import com.ramiro.financeapi.dto.CategoryExpenseResponse;
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

    @Query("""
        SELECT new com.ramiro.financeapi.dto.CategoryExpenseResponse(
            t.category, SUM(t.amount)
        )
        FROM Transaction t
        WHERE t.user = :user AND t.type = 'EXPENSE'
        GROUP BY t.category 
    """)
    List<CategoryExpenseResponse> getExpenseByCategory(User user);

    @Query(value = """
        SELECT
            TO_CHAR(created_at, 'YYYY-MM') AS month,
            COALESCE(SUM(
                CASE
                    WHEN type = 'INCOME'
                    THEN amount
                    ELSE 0
                END
            ), 0) AS income,
            COALESCE(SUM(
                CASE
                    WHEN type = 'EXPENSE'
                    THEN amount
                    ELSE 0
                END
            ), 0) AS expense
        FROM transaction
        WHERE user_id = :userId
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getMonthlySummary(Long userId);

    @Query(value = """
        SELECT
            TO_CHAR(created_at, 'YYYY-MM') AS month,
        
            COALESCE(SUM(
                CASE
                    WHEN type = 'INCOME'
                    THEN amount
                    ELSE -amount
                END
            ), 0) AS balance
        
        FROM transaction
        WHERE user_id = :userId
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getBlanceEvolution(Long userID);
}