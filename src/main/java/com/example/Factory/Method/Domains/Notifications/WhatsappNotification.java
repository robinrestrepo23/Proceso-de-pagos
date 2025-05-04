package com.example.Factory.Method.Domains.Notifications;

public class WhatsappNotification extends Notification {
    private String to;
    private String body;

    public WhatsappNotification(String to, String body, int priority, String recipient) {
        super(to, "whatsapp", recipient, null, body, priority); // Llamamos al constructor de Notification
        this.to = to;
        this.body = body;
    }

    // Getters y Setters
    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }
}

