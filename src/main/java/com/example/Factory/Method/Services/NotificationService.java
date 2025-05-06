package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Factorys.NotificationFactory;
import com.example.Factory.Method.Domains.Notifications.EmailNotification;
import com.example.Factory.Method.Domains.Notifications.Notification;
import com.example.Factory.Method.Domains.Notifications.SmsNotification;
import com.example.Factory.Method.Domains.Notifications.WhatsappNotification;
import com.example.Factory.Method.Domains.Payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationFactory notificationFactory;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired private SmsSenderService smsSenderService;
    @Autowired private WhatsAppSenderService whatsAppSenderService;

    public void sendNotificationAfterPayment(Payment payment) {
        try {
            String type = payment.getNotification();
            String finalBody = buildBody(payment);
            String subject = payment.getSubject() == null ? "Confirmación de pago" : payment.getSubject();

            Notification base = new Notification("", type, payment.getUsername(), subject, finalBody, 1);
            Notification notif = notificationFactory.createNotification(base);

            switch (type.toLowerCase()) {
                case "email" -> emailSenderService.sendEmail((EmailNotification) notif);
                case "sms" -> smsSenderService.sendSms((SmsNotification) notif);
                case "whatsapp" -> whatsAppSenderService.sendWhatsApp((WhatsappNotification) notif);
            }
        } catch (Exception e) {
            System.err.println("❌ Error enviando notificación: " + e.getMessage());
        }
    }

    private String buildBody(Payment p) {
        return """
               ✅ Detalles del pago:
               - Método de pago: %s
               - Valor: $%.2f
               - Valor final: $%.2f
               - Resultado: Pago exitoso

               📣 Mensaje adicional:
               %s
               """.formatted(p.getPaymentMethod(), p.getAmount(), p.getAmountFinal(), p.getBody() != null ? p.getBody() : "");
    }
}

