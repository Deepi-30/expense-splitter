package com.deepika.expensesplitter.dto;

import java.util.*;

    public class SettlementRequest {

        public static class Payer {
            private Long userId;
            private double paidAmount;

            public Long getUserId() { return userId; }
            public void setUserId(Long userId) { this.userId = userId; }

            public double getPaidAmount() { return paidAmount; }
            public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
        }

        private List<Payer> payers;

        public List<Payer> getPayers() { return payers; }
        public void setPayers(List<Payer> payers) { this.payers = payers; }
}
