package com.example.profiles;

import eu.chargetime.ocpp.feature.profile.ClientLocalAuthListEventHandler;
import eu.chargetime.ocpp.model.localauthlist.GetLocalListVersionConfirmation;
import eu.chargetime.ocpp.model.localauthlist.GetLocalListVersionRequest;
import eu.chargetime.ocpp.model.localauthlist.SendLocalListConfirmation;
import eu.chargetime.ocpp.model.localauthlist.SendLocalListRequest;

public class AuthListEvent implements ClientLocalAuthListEventHandler{

    @Override
    public GetLocalListVersionConfirmation handleGetLocalListVersionRequest(GetLocalListVersionRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public SendLocalListConfirmation handleSendLocalListRequest(SendLocalListRequest var1){
        System.out.println(var1);
        return null;
    }
}
