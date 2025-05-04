package com.example.Factory.Method.Domains.Builders;

import com.example.Factory.Method.Domains.Notifications.Notification;
import com.example.Factory.Method.Domains.Notifications.SmsNotification;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationBuilder implements NotificationBuilder {
    private String recipient;
    private String to;
    private String body;
    private int priority;

    public SmsNotificationBuilder setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    @Override
    public SmsNotificationBuilder setSubject(String subject) {
        // SMS no usa subject, puedes manejarlo si lo necesitas
        return this;
    }

    @Override
    public SmsNotificationBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public SmsNotificationBuilder setTo(String to){
        this.to = to;
        return this;
    }
    @Override
    public SmsNotificationBuilder setPriority(int priority){
        this.priority = priority;
        return this;
    }


    @Override
    public Notification build() {
        return new SmsNotification(recipient, to, body, priority);
    }
}
