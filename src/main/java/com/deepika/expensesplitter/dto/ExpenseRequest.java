package com.deepika.expensesplitter.dto;
import java.util.*;
public class ExpenseRequest {
    public static class Person {
        private String name;
        private double paidAmount;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPaidAmount() { return paidAmount; }
        public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    }

    private List<Person> persons;

    public List<Person> getPersons() { return persons; }
    public void setPersons(List<Person> persons) { this.persons = persons; }
}


