package com.example.Factory.Method.Domains.Builders;

import com.example.Factory.Method.Domains.Notifications.Notification;


public interface NotificationBuilder {
    NotificationBuilder setRecipient(String recipient);
    NotificationBuilder setSubject(String subject);
    NotificationBuilder setBody(String body);
    NotificationBuilder setTo(String to);
    NotificationBuilder setPriority(int priority);
    // Este debería estar en la interfaz
    Notification build();
}

