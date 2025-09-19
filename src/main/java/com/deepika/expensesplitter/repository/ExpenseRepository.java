package com.deepika.expensesplitter.repository;

import com.deepika.expensesplitter.model.Expense;
import com.deepika.expensesplitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

}
