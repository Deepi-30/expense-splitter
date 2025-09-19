package com.deepika.expensesplitter.service;

import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.ExpenseShare;
import com.deepika.expensesplitter.model.User;
import com.deepika.expensesplitter.repository.ExpenseShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseShareService {
    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    // Add ExpenseShare entries (who paid what, who owes what)
    public ExpenseShare addExpenseShare(ExpenseShare share) {
        return expenseShareRepository.save(share);
    }

    // Get all shares of a user
    public List<ExpenseShare> getSharesByUser(User user) {
        return expenseShareRepository.findByUser(user);
    }

    // Get all shares of an expense
    public List<ExpenseShare> getSharesByExpense(Expense expense) {
        return expenseShareRepository.findByExpense(expense);
    }

    public double calculateBalance(User user) {
        List<ExpenseShare> shares = expenseShareRepository.findByUser(user);
        double balance = 0;
        for(ExpenseShare s : shares) {
            balance += (s.getAmountPaid() - s.getAmountOwed());
        }
        return balance;
    }
}
