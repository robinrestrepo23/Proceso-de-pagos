package com.example.Factory.Method.Domains.Payments;

import org.springframework.stereotype.Component;

@Component
public class CreditCardPayment implements PaymentProcessor{
    @Override
    public double processPayment(double amount){
        double comissionRate = 0.03;
        double finalAmount = amount + (comissionRate * amount);
        if (amount > 1000){
            finalAmount += 10;
        }
        return finalAmount;
    }
}
