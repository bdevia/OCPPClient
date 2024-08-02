package com.example.request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.business.logic.ChargePoint;
import com.fasterxml.jackson.databind.JsonNode;

import eu.chargetime.ocpp.model.firmware.FirmwareStatus;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;

public class FirmwareRequest extends RequestHandler{
    
    public static CompletableFuture<JsonNode> sendFirmwareStatusNotification(String statusFirmware){
        ChargePoint.getInstance().setStatusFirmware(statusFirmware);
        String status = ChargePoint.getInstance().getStatusFirmware();
        FirmwareStatus chargePointStatusFirmware;

        if(status.equals("DownloadFailed")){
            chargePointStatusFirmware = FirmwareStatus.DownloadFailed;
        }
        else if(status.equals("Downloaded")){
            chargePointStatusFirmware = FirmwareStatus.Downloaded;
        }
        else if(status.equals("Downloading")){
            chargePointStatusFirmware = FirmwareStatus.Downloading;
        }
        else if(status.equals("Idle")){
            chargePointStatusFirmware = FirmwareStatus.Idle;
        }
        else if(status.equals("InstallationFailed")){
            chargePointStatusFirmware = FirmwareStatus.InstallationFailed;
        }   
        else if(status.equals("Installed")){
            chargePointStatusFirmware = FirmwareStatus.Installed;
        }
        else{
            chargePointStatusFirmware = FirmwareStatus.Installing;
        }

        FirmwareStatusNotificationRequest request = new FirmwareStatusNotificationRequest(chargePointStatusFirmware);
        return sendRequest(request);
    } 


    public static void sendFirmwareStatusNotification(String statusFirmware, Integer delay){
        CompletableFuture.delayedExecutor(delay, TimeUnit.SECONDS).execute(() -> {
            sendFirmwareStatusNotification(statusFirmware);
        });
    }
}
