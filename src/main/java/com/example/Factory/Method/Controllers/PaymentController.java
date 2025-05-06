package com.example.Factory.Method.Controllers;

import com.example.Factory.Method.Domains.Payments.Payment;
import com.example.Factory.Method.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.service = paymentService;
    }

    @PostMapping("/process")
    public double processPayment(@RequestParam String paymentType, @RequestParam double amount){
        return service.processPayment(paymentType, amount);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment saved = service.savePayment(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recipient/{recipient}")
    public List<Payment> getPaymentsByRecipient(@PathVariable String recipient) {
        return service.getPaymentsByRecipient(recipient);
    }
}
