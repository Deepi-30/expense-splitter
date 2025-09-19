package com.deepika.expensesplitter.dto;

public class SettlementResponse {
    private String message;

    public SettlementResponse(String message) { this.message = message; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
