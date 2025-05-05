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
            Notification notification
    ) {
        switch (notification.getType().toLowerCase()) {
            case "email":
                return emailNotificationBuilder
                        .setRecipient(notification.getRecipient())
                        .setSubject(notification.getSubject())
                        .setBody(notification.getBody())
                        .setPriority(notification.getPriority())
                        .build();
            case "sms":
                return smsNotificationBuilder
                        .setRecipient(notification.getRecipient())
                        .setBody(notification.getBody())
                        .setPriority(notification.getPriority())
                        .build();
            case "whatsapp":
                return notificationBuilderWhatsapp
                        .setRecipient(notification.getRecipient())
                        .setBody(notification.getBody())
                        .setPriority(notification.getPriority())
                        .build();
            default:
                throw new IllegalArgumentException("Tipo de notificación no soportado: " + notification.getType());
        }
    }
}

