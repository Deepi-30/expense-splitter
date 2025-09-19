package com.deepika.expensesplitter.model;

import jakarta.persistence.*;
@Entity
@Table(name = "expense_shares")
public class ExpenseShare {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = " user_id", nullable = false)
    private User user;

    private Double amountPaid;
    private Double amountOwed;

    public ExpenseShare(){

    }
    public ExpenseShare(Long id, Expense expense, User user, Double amountPaid, Double amountOwed) {
        this.id = id;
        this.expense = expense;
        this.user = user;
        this.amountPaid = amountPaid;
        this.amountOwed = amountOwed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(Double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public Expense getExpense() { return expense; }
    public void setExpense(Expense expense) { this.expense = expense; }
}
