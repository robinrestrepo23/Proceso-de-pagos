package com.example.Factory.Method.Controllers;

import com.example.Factory.Method.Services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public double processPayment(@RequestParam String paymentType, @RequestParam double amount){
        return paymentService.processPayment(paymentType, amount);
    }
}
