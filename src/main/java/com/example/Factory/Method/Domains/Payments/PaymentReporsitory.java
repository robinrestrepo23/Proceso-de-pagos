package com.example.Factory.Method.Domains.Payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentReporsitory extends JpaRepository<Payment, Long> {
    List<Payment> findByRecipient(String recipient);
}
