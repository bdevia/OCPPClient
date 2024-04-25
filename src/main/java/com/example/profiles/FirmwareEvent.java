package com.example.profiles;

import eu.chargetime.ocpp.feature.profile.ClientFirmwareManagementEventHandler;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsConfirmation;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest;
import eu.chargetime.ocpp.model.firmware.UpdateFirmwareConfirmation;
import eu.chargetime.ocpp.model.firmware.UpdateFirmwareRequest;

public class FirmwareEvent implements ClientFirmwareManagementEventHandler {
    
    @Override
    public GetDiagnosticsConfirmation handleGetDiagnosticsRequest(GetDiagnosticsRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public UpdateFirmwareConfirmation handleUpdateFirmwareRequest(UpdateFirmwareRequest var1){
        System.out.println(var1);
        return null;
    }
}
