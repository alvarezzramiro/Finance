package com.ramiro.financeapi.controller;

import com.ramiro.financeapi.dto.ApiResponse;
import com.ramiro.financeapi.dto.CreateTransactionRequest;
import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.service.TransactionService;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HelloController {

    private final TransactionService transactionService;

    public HelloController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hola desde Spring Boot";
    }

    @GetMapping("/api/status")
    public ApiResponse status() {
        return new ApiResponse("API funcionando", "1.0");
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/transactions/type/{type}")
    public List<Transaction> getTransctionByType(@PathVariable TransactionType type) {
        return transactionService.getTransactionByType(type);
    }

    @GetMapping("/transactions/category/{category}")
    public List<Transaction> getTransctionByCategory(@PathVariable TransactionCategory category) {
        return transactionService.getTransactionByCategory(category);
    }
}
