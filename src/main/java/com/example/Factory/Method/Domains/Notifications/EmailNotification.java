package com.example.Factory.Method.Domains.Notifications;

import java.util.List;

public class EmailNotification extends Notification {
    private final List<String> cc;
    private final List<String> bcc;
    private final List<String> attachments;

    public EmailNotification(String to, String recipient, String subject, String body,
                             List<String> cc, List<String> bcc,
                             List<String> attachments, int priority) {
        // Llama al constructor de Notification con los parámetros adecuados
        super(to, "email", recipient, subject, body, priority);
        this.cc = cc;
        this.bcc = bcc;
        this.attachments = attachments;
    }

    // Getters y Setters
    public List<String> getCc() { return cc; }
    public List<String> getBcc() { return bcc; }
    public List<String> getAttachments() { return attachments; }
}


//@Service
//public class EmailNotification implements Notification {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    private String recipient;
//
//    public void setRecipient(String email) {
//        this.recipient = email;
//    }
//
//    @Override
//    public void notifyUser(String message) {
//        if (recipient == null || recipient.isEmpty()) return;
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(recipient);
//        mailMessage.setSubject("Notificación de pago");
//        mailMessage.setText(message);
//        mailSender.send(mailMessage);
//    }
//}
