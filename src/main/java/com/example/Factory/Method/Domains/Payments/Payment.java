package com.example.Factory.Method.Domains.Payments;


import java.util.concurrent.atomic.AtomicInteger;

public class Payment {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private int transactionId;
    private String paymentMethod;
    private double amount;
    private double amountFinal;
    private String username;
    private String status;

    public Payment(String paymentMethod, double amount, double result, String recipient, String exitoso) {
        this.transactionId = ID_GENERATOR.getAndIncrement();
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.amountFinal = result;
        this.username = recipient;
        this.status = exitoso;
    }

    public int getTransactionId() {
        return transactionId;
    }

    // Métodos set/get normales
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountFinal() {
        return amountFinal;
    }

    public void setAmountFinal(double amountFinal) {
        this.amountFinal = amountFinal;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

