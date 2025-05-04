package com.example.Factory.Method.Domains.Notifications;

public class Notification {
    private String to;
    private String type;
    private String recipient;
    private String subject;
    private String body;
    private int priority;

    public Notification(String to, String type, String recipient, String subject, String body, int priority) {
        this.to = to;
        this.type = type;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.priority = priority;
    }
    // Getters y Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

