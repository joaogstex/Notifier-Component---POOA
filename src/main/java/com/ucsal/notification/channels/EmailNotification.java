package com.ucsal.notification.channels;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.ucsal.notification.annotations.NoticationChannelType;
import com.ucsal.notification.interfaces.NotificationChannel;
import com.ucsal.notification.models.Notification;

@NoticationChannelType("email")
public class EmailNotification implements NotificationChannel {
    private String host;
    private String user;
    private String apiToken;

    public EmailNotification(String host, String user, String apiToken) {
        this.host = host;
        this.user = user;
        this.apiToken = apiToken;
    }

    @Override
    public void send(Notification notification) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", "2525");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, apiToken);
            }
        };

        Session session = Session.getInstance(properties, authenticator);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(notification.getRecipient()));
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            Transport.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}