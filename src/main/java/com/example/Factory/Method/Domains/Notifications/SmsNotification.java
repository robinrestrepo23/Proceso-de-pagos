package com.example.Factory.Method.Domains.Notifications;


public class SmsNotification extends Notification {
    private String recipient;
    private String to;
    private String body;

    public SmsNotification(String recipient, String to, String body, int priority) {
        super(to, "sms", recipient, null, body, priority); // Llamamos al constructor de Notification
        this.recipient = recipient;
        this.to = to;
        this.body = body;
    }

    // Getters y Setters
    public String getRecipient() {
        return recipient;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }
}


