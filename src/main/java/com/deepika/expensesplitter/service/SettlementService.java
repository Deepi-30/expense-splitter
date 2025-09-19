package com.deepika.expensesplitter.service;

import com.deepika.expensesplitter.dto.SettlementRequest;
import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.ExpenseShare;
import com.deepika.expensesplitter.model.User;
import com.deepika.expensesplitter.repository.ExpenseRepository;
import com.deepika.expensesplitter.repository.ExpenseShareRepository;
import com.deepika.expensesplitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    @Autowired
    private UserRepository userRepository;

    static class UserBalance {
        User user;
        double balance;

        UserBalance(User user, double balance) {
            this.user = user;
            this.balance = balance;
        }

    }
        public List<String> calculateSettlement(Expense expense, SettlementRequest request) {

            List<UserBalance> userBalances = new ArrayList<>();
            double totalAmount = 0;

            for (SettlementRequest.Payer p : request.getPayers()) {
                User user = userRepository.findById(p.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found: " + p.getUserId()));

                // Save expense share
                ExpenseShare share = new ExpenseShare();
                share.setExpense(expense);
                share.setUser(user);
                share.setAmountPaid(p.getPaidAmount());
                expenseShareRepository.save(share);

                userBalances.add(new UserBalance(user, p.getPaidAmount()));
                totalAmount += p.getPaidAmount();
            }

            double equalShare = totalAmount / userBalances.size();
            for (UserBalance ub : userBalances) {
                ub.balance -= equalShare;  // positive = should receive, negative = should pay
            }

            List<UserBalance> creditors = new ArrayList<>();
            List<UserBalance> debtors = new ArrayList<>();
            for (UserBalance ub : userBalances) {
                if (ub.balance > 0) creditors.add(ub);
                else if (ub.balance < 0) debtors.add(ub);
            }

            List<String> settlements = new ArrayList<>();
            settlements.add("Total Spent: ₹" + totalAmount);
            settlements.add("Each person should pay: ₹" + equalShare);

            int i = 0, j = 0;
            while (i < debtors.size() && j < creditors.size()) {
                UserBalance debtor = debtors.get(i);
                UserBalance creditor = creditors.get(j);

                double amountToPay = Math.min(-debtor.balance, creditor.balance);
                settlements.add(debtor.user.getName() + " pays ₹" + amountToPay + " to " + creditor.user.getName());

                debtor.balance += amountToPay;
                creditor.balance -= amountToPay;

                if (Math.abs(debtor.balance) < 0.01) i++;
                if (Math.abs(creditor.balance) < 0.01) j++;
            }
            return settlements;

        }

}
