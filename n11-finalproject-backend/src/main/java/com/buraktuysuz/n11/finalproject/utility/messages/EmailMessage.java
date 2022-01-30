package com.buraktuysuz.n11.finalproject.utility.messages;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailMessage extends BaseMessage {

    private String username;
    private String password;
    private Properties prop;

    public EmailMessage(String username,String password,Properties prop){
        this.username=username;
        this.password=password;
        this.prop=prop;
    }

    @Override
    public boolean sendMessage(String sendMessage,String to) {

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("fromMail")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("Kredi Başvuru Sonucu - N11 Talenthub final project");
            message.setText(sendMessage);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
