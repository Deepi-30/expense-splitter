package com.deepika.expensesplitter.service;

import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.User;
import com.deepika.expensesplitter.repository.ExpenseRepository;
import com.deepika.expensesplitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    // Add new expense
    public Expense addExpense(Expense expense) {
        // Fetch the managed User from DB
        User payer = userRepository.findById(expense.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        expense.setUser(payer);

        // createdAt will be set automatically by @PrePersist
        return expenseRepository.save(expense);
    }
    // List all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Get expenses by user
    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }
}
