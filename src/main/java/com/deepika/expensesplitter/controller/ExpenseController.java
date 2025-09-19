package com.deepika.expensesplitter.controller;

import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.ExpenseShare;
import com.deepika.expensesplitter.model.User;
import com.deepika.expensesplitter.service.ExpenseService;
import com.deepika.expensesplitter.service.ExpenseShareService;
import com.deepika.expensesplitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseShareService expenseShareService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense){
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/user/{userId}")
    public List<Expense> getExpenseByUser(@PathVariable Long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
        return expenseService.getExpensesByUser(user);
    }

    @GetMapping("/user/{userid}/balance")
    public double getUserBalance(@PathVariable Long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseShareService.calculateBalance(user);
    }

    @PostMapping("/shares")
    public ExpenseShare addExpenseShare(@RequestBody ExpenseShare share){
        return expenseShareService.addExpenseShare(share);
    }
}
