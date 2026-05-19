package com.ramiro.financeapi.dto;

import java.math.BigDecimal;

public class MonthlySummaryResponse {

    private String month;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;

    public MonthlySummaryResponse(String month, BigDecimal income, BigDecimal expense) {
        this.month = month;
        this.income = income;
        this.expense = expense;
        this.balance = income.subtract(expense);
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
