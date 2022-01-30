package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;
import com.buraktuysuz.n11.finalproject.utility.enums.EnumMessageType;
import com.buraktuysuz.n11.finalproject.utility.messages.BaseMessage;
import com.buraktuysuz.n11.finalproject.utility.messages.MessageFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public void sendMessage(CreditApplication application, EnumMessageType type) {

        boolean messageResult=false;
        switch (application.getEnumApplicationResult()) {
            case APPROVED:
                messageResult= sendApprovalMessage(application, type);
            break;
            case NOTAPPROVED:
                messageResult= sendDisapproveMessage(application, type);
        }
    }

    private boolean sendApprovalMessage(CreditApplication application, EnumMessageType type) {
        BaseMessage baseMessage = MessageFactory.createMessage(type);
        var customer=application.getCustomer();
        String message = "Sayın "+customer.getFirstName()+" " +customer.getLastName() +" Tebrikler " + application.getCreditLimit().toString() + " limitli krediniz ONAYLANMIŞTIR";
        return baseMessage.sendMessage(message, application.getCustomer().getCurrentEmail());
    }

    private boolean sendDisapproveMessage(CreditApplication application, EnumMessageType type) {
        BaseMessage baseMessage = MessageFactory.createMessage(type);
        var customer=application.getCustomer();
        String message = "Sayın "+customer.getFirstName()+" " +customer.getLastName() +" Üzgünüz krediniz ONAYLANMAMIŞTIR";
        return baseMessage.sendMessage(message, application.getCustomer().getCurrentEmail());
    }
}
