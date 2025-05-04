package com.example.Factory.Method.Domains.Factorys;

import com.example.Factory.Method.Domains.Payments.CreditCardPayment;
import com.example.Factory.Method.Domains.Payments.DebitCardPayment;
import com.example.Factory.Method.Domains.Payments.PayPalPayment;
import com.example.Factory.Method.Domains.Payments.PaymentProcessor;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessorFactory {
    public PaymentProcessor getPaymentProcessor(String paymentType){
        return switch (paymentType.toUpperCase()){
            case "CREDIT_CARD" -> new CreditCardPayment();
            case "DEBIT_CARD" -> new DebitCardPayment();
            case "PAYPAL" -> new PayPalPayment();
            default -> throw new IllegalArgumentException("Metodo de pago incorrecto");
        };
    }
}
