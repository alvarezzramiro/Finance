package com.ramiro.financeapi.dto;

import java.math.BigDecimal;

public class BalanceEvolutionResponse {

    private String month;
    private BigDecimal balance;

    public BalanceEvolutionResponse(String month, BigDecimal balance) {
        this.month = month;
        this.balance = balance;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
