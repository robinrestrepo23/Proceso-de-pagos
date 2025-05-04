package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Notifications.EmailNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailNotification email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Cambié getTo() a getRecipient()
        helper.setTo(email.getRecipient());
        helper.setSubject(email.getSubject());
        helper.setPriority(email.getPriority());
        helper.setText(email.getBody(), true);

        if (!email.getCc().isEmpty()) {
            helper.setCc(email.getCc().toArray(new String[0]));
        }

        if (!email.getBcc().isEmpty()) {
            helper.setBcc(email.getBcc().toArray(new String[0]));
        }

        for (String path : email.getAttachments()) {
            FileSystemResource file = new FileSystemResource(new File(path));
            helper.addAttachment(file.getFilename(), file);
        }

        mailSender.send(message);
    }
}
