package com.buraktuysuz.n11.finalproject.utility.messages;

import com.buraktuysuz.n11.finalproject.utility.enums.EnumMessageType;

import java.util.Properties;

public class MessageFactory {

    public static BaseMessage createMessage(EnumMessageType type){

        BaseMessage message = null;
        switch (type){
            case GMAIL: // gmail smtp
                Properties prop = new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("fromMail", "n11-final-project@test.com");
                prop.put("mail.smtp.port", "465");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.ssl.enable", "true");
                prop.put("mail.smtp.socketFactory.port", "465");
                prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                message=new EmailMessage("gmail@gmail.com","gmail_password",prop);
                break;
            case OFFICE365:
                Properties offprop = new Properties();
                offprop.put("mail.smtp.host", "smtp.office365.com");
                offprop.put("fromMail", "info@snevars.com"); // office365 mail sadece kayıtlı ve izinli mailler üzerinden mail göndermeye izin veriyor
                offprop.put("mail.smtp.starttls.enable","true");
                offprop.put("mail.smtp.port", "587");
                offprop.put("mail.smtp.auth", "true");
                offprop.put("mail.smtp.socketFactory.port", "587");
                offprop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                message=new EmailMessage("info@snevars.com","snevars.info",offprop);
                break;
            case SMS:

                break;
        }
        return message;
    }


}
