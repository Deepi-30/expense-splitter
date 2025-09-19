package com.deepika.expensesplitter.repository;

import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.ExpenseShare;
import com.deepika.expensesplitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Long> {
    List<ExpenseShare> findByUser(User user);
    List<ExpenseShare> findByExpense(Expense expense);

}
