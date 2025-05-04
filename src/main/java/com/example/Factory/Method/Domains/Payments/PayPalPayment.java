package com.example.Factory.Method.Domains.Payments;

import org.springframework.stereotype.Component;

@Component
public class PayPalPayment implements PaymentProcessor{
    @Override
    public double processPayment(double amount) {
        double comissionRate = 0.02;
        double finalAmount = amount + (comissionRate * amount);
        if (amount > 750) {
            finalAmount += 7;
        }
        return finalAmount;
    }
}
