package com.example.profiles;

import eu.chargetime.ocpp.feature.profile.ClientLocalAuthListEventHandler;
import eu.chargetime.ocpp.model.localauthlist.GetLocalListVersionConfirmation;
import eu.chargetime.ocpp.model.localauthlist.GetLocalListVersionRequest;
import eu.chargetime.ocpp.model.localauthlist.SendLocalListConfirmation;
import eu.chargetime.ocpp.model.localauthlist.SendLocalListRequest;
import eu.chargetime.ocpp.model.localauthlist.UpdateStatus;

public class AuthListEvent implements ClientLocalAuthListEventHandler{

    @Override
    public GetLocalListVersionConfirmation handleGetLocalListVersionRequest(GetLocalListVersionRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public SendLocalListConfirmation handleSendLocalListRequest(SendLocalListRequest request){
        System.out.println(request);
        System.out.println(request.getListVersion());
        return new SendLocalListConfirmation(UpdateStatus.Accepted);
    }
}
