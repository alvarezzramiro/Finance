package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.CreateTransactionRequest;
import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction createTransaction(
            CreateTransactionRequest request
    ) {
        Transaction transaction = new Transaction();

        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());

        return repository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public List<Transaction> getTransactionByType(TransactionType type) {
        return repository.findByType(type);
    }

    public List<Transaction> getTransactionByCategory(TransactionCategory category) {
        return repository.findByCategory(category);
    }
}
