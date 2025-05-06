package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Payments.Payment;
import com.example.Factory.Method.Domains.Payments.PaymentProcessor;
import com.example.Factory.Method.Domains.Factorys.PaymentProcessorFactory;
import com.example.Factory.Method.Domains.Payments.PaymentReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentProcessorFactory paymentProcessorFactory;
    private final PaymentReporsitory repository;
    @Autowired
    private NotificationService notificationService; // crea esta clase


    @Autowired
    public PaymentService(PaymentProcessorFactory paymentProcessorFactory, PaymentReporsitory paymentReporsitory){
        this.paymentProcessorFactory = paymentProcessorFactory;
        this.repository = paymentReporsitory;
    }

    public double processPayment(String paymentType, double amount){
        PaymentProcessor paymentProcessor = paymentProcessorFactory.getPaymentProcessor(paymentType);
        return  paymentProcessor.processPayment(amount);
    }

    public Payment savePayment(Payment payment) {
        double finalAmount = paymentProcessorFactory
                .getPaymentProcessor(payment.getPaymentMethod())
                .processPayment(payment.getAmount());

        payment.setAmountFinal(finalAmount);
        payment.setTimestamp(LocalDateTime.now());
        Payment savedPayment = repository.save(payment);
        notificationService.sendNotificationAfterPayment(savedPayment);
        return repository.save(payment);
    }
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return repository.findById(id);
    }

    public List<Payment> getPaymentsByRecipient(String username) {
        return repository.findByRecipient(username);
    }

    public boolean deletePaymentById(Long id) {
        Optional<Payment> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
