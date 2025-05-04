package com.example.Factory.Method.Domains.Builders;


import com.example.Factory.Method.Domains.Notifications.Notification;
import com.example.Factory.Method.Domains.Notifications.WhatsappNotification;
import org.springframework.stereotype.Component;

@Component
public class NotificationBuilderWhatsapp implements NotificationBuilder {

    private String to;
    private String recipient;
    private String body;
    private int priority;

    public NotificationBuilderWhatsapp to(String to) {
        this.to = to;
        return this;
    }

    public NotificationBuilderWhatsapp recipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public NotificationBuilderWhatsapp body(String body) {
        this.body = body;
        return this;
    }

    public NotificationBuilderWhatsapp priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public NotificationBuilder setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    @Override
    public NotificationBuilder setSubject(String subject) {
        return this;
    }

    @Override
    public NotificationBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public NotificationBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    @Override
    public NotificationBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public Notification build() {
        return new WhatsappNotification(to,body, priority, recipient);
    }
}
