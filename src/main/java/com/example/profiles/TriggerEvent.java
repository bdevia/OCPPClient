package com.example.profiles;

import com.example.business.logic.ChargePoint;
import com.example.request.CoreRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            CoreRequest.sendBootNotification();
            return new TriggerMessageConfirmation(TriggerMessageStatus.Accepted);
        }
        else if(request.getRequestedMessage() == TriggerMessageRequestType.StatusNotification){
            CoreRequest.sendStatusNotification(new ObjectMapper().createObjectNode().put("status", ChargePoint.getInstance().getStatus()));
            return new TriggerMessageConfirmation(TriggerMessageStatus.Accepted);
        }
        
        return null;
    }
}
