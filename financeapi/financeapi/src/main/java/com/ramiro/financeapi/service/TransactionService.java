package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.BalanceResponse;
import com.ramiro.financeapi.dto.CreateTransactionRequest;
import com.ramiro.financeapi.dto.TransactionResponse;
import com.ramiro.financeapi.dto.UpdateTransactionRequest;
import com.ramiro.financeapi.entity.Transaction;
import com.ramiro.financeapi.entity.TransactionCategory;
import com.ramiro.financeapi.entity.TransactionType;
import com.ramiro.financeapi.entity.User;
import com.ramiro.financeapi.exception.ResourceNotFoundException;
import com.ramiro.financeapi.repository.TransactionRepository;
import com.ramiro.financeapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse createTransaction(
            CreateTransactionRequest request
    ) {
        Transaction transaction = new Transaction();

        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());

        User user = getAuthenticatedUser();
        transaction.setUser(user);

        Transaction savedTransaction =  transactionRepository.save(transaction);

        return new TransactionResponse(
            savedTransaction.getId(),
            savedTransaction.getTitle(),
            savedTransaction.getAmount(),
            savedTransaction.getType(),
            savedTransaction.getCategory()
        );
    }

    public TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request
    ) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());

        Transaction updatedTransaction =  transactionRepository.save(transaction);

        return new TransactionResponse(
                updatedTransaction.getId(),
                updatedTransaction.getTitle(),
                updatedTransaction.getAmount(),
                updatedTransaction.getType(),
                updatedTransaction.getCategory()
        );
    }

    public void deleteTransaction(Long id) {

        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found with id: " + id
                        )
                );

        transactionRepository.delete(transaction);
    }

    public List<Transaction> getAllTransactions() {
        User user = getAuthenticatedUser();
        return transactionRepository.findByUser(user);
    }

    public List<Transaction> getTransactionByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getTransactionByCategory(TransactionCategory category) {
        return transactionRepository.findByCategory(category);
    }

    public BalanceResponse getBalance() {

        List<Transaction> transactions = transactionRepository.findAll();

        double income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = income - expense;

        return new BalanceResponse(income, expense, balance);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
