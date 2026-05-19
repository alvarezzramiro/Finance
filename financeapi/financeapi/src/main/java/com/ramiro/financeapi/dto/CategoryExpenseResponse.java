package com.ramiro.financeapi.dto;

import com.ramiro.financeapi.entity.TransactionCategory;

import java.math.BigDecimal;

public class CategoryExpenseResponse {

    private TransactionCategory category;
    private BigDecimal total;

    public CategoryExpenseResponse(TransactionCategory category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
