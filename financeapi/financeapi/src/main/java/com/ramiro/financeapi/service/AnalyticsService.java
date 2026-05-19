package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.AnalyticsResponse;
import com.ramiro.financeapi.dto.BalanceEvolutionResponse;
import com.ramiro.financeapi.dto.CategoryExpenseResponse;
import com.ramiro.financeapi.dto.MonthlySummaryResponse;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.entity.User;
import com.ramiro.financeapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public AnalyticsService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public AnalyticsResponse getSummary() {
        User user = userService.getAuthenticatedUser();

        BigDecimal income = transactionRepository.getTotalIncome(user);
        BigDecimal expense = transactionRepository.getTotalExpense(user);
        BigDecimal balance = income.subtract(expense);
        long count = transactionRepository.countByUser(user);
        long incomeCount = transactionRepository.countByUserAndType(user, TransactionType.INCOME);
        long expenseCount = transactionRepository.countByUserAndType(user, TransactionType.EXPENSE);

        return new AnalyticsResponse(income, expense, balance, count, incomeCount, expenseCount);
    }

    public List<CategoryExpenseResponse> getExpenseByCategory() {
        User user = userService.getAuthenticatedUser();
        return transactionRepository.getExpenseByCategory(user);
    }

    public List<MonthlySummaryResponse>
    getMonthlySummary() {

        User user = userService.getAuthenticatedUser();

        List<Object[]> results =
                transactionRepository
                        .getMonthlySummary(user.getId());

        return results.stream()
                .map(row -> new MonthlySummaryResponse(
                        (String) row[0],
                        (BigDecimal) row[1],
                        (BigDecimal) row[2]
                ))
                .toList();
    }

    public List<BalanceEvolutionResponse> getBalanceEvolution() {
        User user = userService.getAuthenticatedUser();

        List<Object[]> results = transactionRepository.getBlanceEvolution(user.getId());

        return results.stream().map(row -> new BalanceEvolutionResponse(
                (String) row[0],
                (BigDecimal) row[1]
        )).toList();
    }
}
