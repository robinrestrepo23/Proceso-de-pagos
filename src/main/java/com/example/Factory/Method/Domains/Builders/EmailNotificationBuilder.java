package com.example.Factory.Method.Domains.Builders;

import com.example.Factory.Method.Domains.Notifications.EmailNotification;
import com.example.Factory.Method.Domains.Notifications.Notification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailNotificationBuilder implements NotificationBuilder {
    private String to;
    private String subject;
    private String body;
    private String recipient;
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private List<String> attachments = new ArrayList<>();
    private int priority;

    public EmailNotificationBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EmailNotificationBuilder recipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public EmailNotificationBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailNotificationBuilder body(String body) {
        this.body = body;
        return this;
    }

    public EmailNotificationBuilder cc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailNotificationBuilder bcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailNotificationBuilder attachments(List<String> attachments) {
        this.attachments = attachments;
        return this;
    }

    public EmailNotificationBuilder priority(int priority) {
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
        this.subject = subject;
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
        return new EmailNotification(to, recipient, subject, body, cc, bcc, attachments, priority);
    }
}


