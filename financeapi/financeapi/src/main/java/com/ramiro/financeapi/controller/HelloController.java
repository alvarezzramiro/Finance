package com.ramiro.financeapi.controller;

import com.ramiro.financeapi.dto.ApiResponse;
import com.ramiro.financeapi.dto.CreateTransactionRequest;
import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.service.TransactionService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }
}
