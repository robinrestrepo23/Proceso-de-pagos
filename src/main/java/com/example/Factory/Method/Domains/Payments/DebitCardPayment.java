package com.example.Factory.Method.Domains.Payments;

import org.springframework.stereotype.Component;

@Component
public class DebitCardPayment implements PaymentProcessor {
    @Override
    public double processPayment(double amount) {
        double comissionRate = 0.01;
        double finalAmount = amount + (comissionRate * amount);
        if (amount > 500) {
            finalAmount += 5;
        }
        return finalAmount;
    }
}
