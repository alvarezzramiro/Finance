package com.ramiro.financeapi.dto;

import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private String title;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TransactionResponse(Long id, String title, BigDecimal amount, TransactionType type, TransactionCategory category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
