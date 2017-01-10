package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendTo(User user) {
        new Thread(() -> {
            final Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            final String username = "bdzjn.co@gmail.com";
            final String password = "lunjomazobozavanje";
            final String subject = "Verifikacija naloga";

            final String messageText = String.format("Uspešno ste se registrovali na Poretti.\n\nhttp://localhost:8080/#!/verifyAccount/%d.\n\nMolimo Vas da klikom na link iznad potvrdite svoju registraciju.", user.getId());

            final Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                final Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setSubject(subject);
                message.setText(messageText);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
