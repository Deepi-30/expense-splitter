package com.deepika.expensesplitter.controller;

import com.deepika.expensesplitter.dto.SettlementRequest;
import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.ExpenseShare;
import com.deepika.expensesplitter.service.ExpenseService;
import com.deepika.expensesplitter.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/settlements")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expense/{expenseId}")
    public List<String> settleExpense(@PathVariable Long expenseId, @RequestBody SettlementRequest request) {
        Expense expense = expenseService.getExpenseById(expenseId);
        return settlementService.calculateSettlement(expense, request);
    }

    @GetMapping("/expense/{expenseId}")
    public List<String> getExpenseSettlement(@PathVariable Long expenseId){
        Expense expense = expenseService.getExpenseById(expenseId);
        List<ExpenseShare> shares = expense.getShares();

        SettlementRequest request = new SettlementRequest();
        List<SettlementRequest.Payer> payers = new ArrayList<>();

        for (ExpenseShare share : shares) {
            SettlementRequest.Payer p = new SettlementRequest.Payer();
            p.setUserId(share.getUser().getId());
            p.setPaidAmount(share.getAmountPaid());
            payers.add(p);
        }

        request.setPayers(payers);

        // Calculate settlements using existing service
        return settlementService.calculateSettlement(expense, request);


    }

}
