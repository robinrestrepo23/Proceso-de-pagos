package com.example.Factory.Method.Services;

import com.example.Factory.Method.Domains.Notifications.Notification;

import com.example.Factory.Method.Domains.Notifications.SmsNotification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public static final String accountSid = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String authToken = System.getenv("TWILIO_AUTH_TOKEN");

    public void sendSms(SmsNotification smsNotification) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber("+57"+smsNotification.getRecipient()),
                new PhoneNumber(fromPhoneNumber),
                smsNotification.getBody()
        ).create();

        System.out.println("SMS enviado con SID: " + message.getSid());
    }
}
