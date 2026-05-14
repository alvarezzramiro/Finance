package com.ramiro.financeapi.dto;

import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;

public class TransactionResponse {
    private Long id;
    private String title;
    private double amount;
    private TransactionType type;
    private TransactionCategory category;

    public TransactionResponse(
        Long id,
        String title,
        double amount,
        TransactionType type,
        TransactionCategory category
    ) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionCategory getCategory() {
        return category;
    }
}
