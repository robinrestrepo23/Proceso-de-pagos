package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Notifications.Notification;
import com.example.Factory.Method.Domains.Builders.NotificationBuilderWhatsapp;
import com.example.Factory.Method.Domains.Notifications.WhatsappNotification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppSenderService {

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public static final String accountSid = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String authToken = System.getenv("TWILIO_AUTH_TOKEN");

    public void sendWhatsApp(WhatsappNotification whatsAppNotification) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber("whatsapp: +57 " + whatsAppNotification.getRecipient()),
                new PhoneNumber("whatsapp:" + fromPhoneNumber),
                whatsAppNotification.getBody()
        ).create();
        System.out.println("Destinatario de WhatsApp: " + whatsAppNotification.getRecipient());


        System.out.println("WhatsApp enviado con SID: " + message.getSid());
    }
}