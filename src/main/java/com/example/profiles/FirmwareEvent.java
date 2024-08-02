package com.example.profiles;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.request.FirmwareRequest;

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
        System.out.println(var1.getRetrieveDate());
        ZonedDateTime now = ZonedDateTime.now().plusHours(-4);
        Duration duration = Duration.between(now, var1.getRetrieveDate());
        long secondsRemaining = duration.getSeconds();
        System.out.println(secondsRemaining);

        CompletableFuture.delayedExecutor(secondsRemaining, TimeUnit.SECONDS)
        .execute(() -> {
            FirmwareRequest.sendFirmwareStatusNotification("Downloading", 1);
            FirmwareRequest.sendFirmwareStatusNotification("Downloaded", 30);
            FirmwareRequest.sendFirmwareStatusNotification("Installing", 35);
            FirmwareRequest.sendFirmwareStatusNotification("Installed", 60);
        });

        return new UpdateFirmwareConfirmation();
    }
}
