package com.example.Factory.Method.Domains.Payments;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Payment {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String paymentMethod;
    private double amount;
    private double amountFinal;
    private String body;
    private String subject;
    private String notification;
    private String recipient;
    private String status;
    private LocalDateTime timestamp;


    public Payment(String paymentMethod, double amount, double result, String body, String subject, String notification, String recipient, String exitoso) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.amountFinal = result;
        this.body = body;
        this.subject = subject;
        this.notification = notification;
        this.recipient = recipient;
        this.status = exitoso;
    }

    public Payment() {

    }

    public Long getTransactionId() {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setUsername(String username) {
        this.recipient = username;
    }

    public String getUsername() {
        return recipient;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

