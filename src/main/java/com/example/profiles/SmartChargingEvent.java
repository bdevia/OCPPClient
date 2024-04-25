package com.example.profiles;

import eu.chargetime.ocpp.feature.profile.ClientSmartChargingEventHandler;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileConfirmation;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileRequest;
import eu.chargetime.ocpp.model.smartcharging.GetCompositeScheduleConfirmation;
import eu.chargetime.ocpp.model.smartcharging.GetCompositeScheduleRequest;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileConfirmation;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;

public class SmartChargingEvent implements ClientSmartChargingEventHandler{
    
    @Override
    public SetChargingProfileConfirmation handleSetChargingProfileRequest(SetChargingProfileRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public ClearChargingProfileConfirmation handleClearChargingProfileRequest(ClearChargingProfileRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public GetCompositeScheduleConfirmation handleGetCompositeScheduleRequest(GetCompositeScheduleRequest var1){
        System.out.println(var1);
        return null;
    }
}
