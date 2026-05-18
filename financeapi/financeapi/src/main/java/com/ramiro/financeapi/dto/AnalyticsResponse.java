package com.ramiro.financeapi.dto;

import java.math.BigDecimal;

public class AnalyticsResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private long transactionsCount;
    private long incomeCount;
    private long expenseCount;

    public AnalyticsResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance, long transactionsCount, long incomeCount, long expenseCount) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.transactionsCount = transactionsCount;
        this.incomeCount = incomeCount;
        this.expenseCount = expenseCount;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getTransactionsCount() {
        return transactionsCount;
    }

    public long getIncomeCount() {
        return incomeCount;
    }

    public long getExpenseCount() {
        return expenseCount;
    }
}
