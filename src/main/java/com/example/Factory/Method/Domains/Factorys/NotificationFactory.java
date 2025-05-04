package com.example.Factory.Method.Domains.Factorys;

import com.example.Factory.Method.Domains.Builders.EmailNotificationBuilder;
import com.example.Factory.Method.Domains.Builders.NotificationBuilder;
import com.example.Factory.Method.Domains.Builders.SmsNotificationBuilder;
import com.example.Factory.Method.Domains.Builders.NotificationBuilderWhatsapp;
import com.example.Factory.Method.Domains.Notifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    private final EmailNotificationBuilder emailNotificationBuilder;
    private final SmsNotificationBuilder smsNotificationBuilder;
    private final NotificationBuilderWhatsapp notificationBuilderWhatsapp;

    @Autowired
    public NotificationFactory(EmailNotificationBuilder emailNotificationBuilder,
                               SmsNotificationBuilder smsNotificationBuilder,
                               NotificationBuilderWhatsapp notificationBuilderWhatsapp) {
        this.emailNotificationBuilder = emailNotificationBuilder;
        this.smsNotificationBuilder = smsNotificationBuilder;
        this.notificationBuilderWhatsapp = notificationBuilderWhatsapp;
    }

    public Notification createNotification(
            String type,
            String recipient,
            String subject,
            String body,
            int priority
    ) {
        if ("email".equalsIgnoreCase(type)) {
            return emailNotificationBuilder
                    .recipient(recipient)
                    .subject(subject)
                    .body(body)
                    .priority(priority)
                    .build();
        } else if ("sms".equalsIgnoreCase(type)) {
            return smsNotificationBuilder
                    .setRecipient(recipient)
                    .setBody(body)
                    .build();
        } else if ("whatsapp".equalsIgnoreCase(type)) {
            return notificationBuilderWhatsapp
                    .setRecipient(recipient)
                    .setBody(body)
                    .build();
        } else {
            throw new IllegalArgumentException("Tipo de notificación no soportado: " + type);
        }
    }
}

