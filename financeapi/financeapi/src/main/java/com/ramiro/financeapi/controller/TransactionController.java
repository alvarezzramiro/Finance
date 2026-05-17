package com.ramiro.financeapi.controller;

import com.ramiro.financeapi.dto.*;
import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.service.TransactionService;


import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/status")
    public ApiResponse status() {
        return new ApiResponse("API funcionando", "1.0");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(

            @RequestParam(required = false)
            LocalDate startDate,

            @RequestParam(required = false)
            LocalDate endDate,

            @ParameterObject Pageable pageable
    ) {
        return transactionService.getTransactions(startDate, endDate, pageable);
    }

    @GetMapping("/type/{type}")
    public List<Transaction> getTransctionByType(@PathVariable TransactionType type) {
        return transactionService.getTransactionByType(type);
    }

    @GetMapping("/category/{category}")
    public List<Transaction> getTransctionByCategory(@PathVariable TransactionCategory category) {
        return transactionService.getTransactionByCategory(category);
    }

    @GetMapping("/balance")
    public BalanceResponse getBalance() {
        return transactionService.getBalance();
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request
    ) {
        return transactionService.updateTransaction(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction ( @PathVariable Long id ) {
        transactionService.deleteTransaction(id);
    }
}
