package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Payments.PaymentProcessor;
import com.example.Factory.Method.Domains.Factorys.PaymentProcessorFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentProcessorFactory paymentProcessorFactory;

    public PaymentService(PaymentProcessorFactory paymentProcessorFactory){
        this.paymentProcessorFactory = paymentProcessorFactory;
    }

    public double processPayment(String paymentType, double amount){
        PaymentProcessor paymentProcessor = paymentProcessorFactory.getPaymentProcessor(paymentType);
        return  paymentProcessor.processPayment(amount);
    }
}
