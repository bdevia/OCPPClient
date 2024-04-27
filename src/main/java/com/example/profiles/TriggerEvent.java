package com.example.profiles;

import com.example.request.CoreRequest;

import eu.chargetime.ocpp.feature.profile.ClientRemoteTriggerEventHandler;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageConfirmation;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequestType;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageStatus;

public class TriggerEvent implements ClientRemoteTriggerEventHandler {
    
    @Override
    public TriggerMessageConfirmation handleTriggerMessageRequest(TriggerMessageRequest request){
        System.out.println(request);
        if(request.getRequestedMessage() == TriggerMessageRequestType.BootNotification){
            CoreRequest.sendBootNotification(3);
            return new TriggerMessageConfirmation(TriggerMessageStatus.Accepted);
        }
        else if(request.getRequestedMessage() == TriggerMessageRequestType.StatusNotification){
            CoreRequest.sendStatusNotification(3);
            return new TriggerMessageConfirmation(TriggerMessageStatus.Accepted);
        }
        return null;
    }
}
