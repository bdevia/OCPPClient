package com.example.request;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.business.logic.ChargePoint;
import com.fasterxml.jackson.databind.JsonNode;

import eu.chargetime.ocpp.model.core.BootNotificationRequest;
import eu.chargetime.ocpp.model.core.ChargePointErrorCode;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import eu.chargetime.ocpp.model.core.StartTransactionRequest;
import eu.chargetime.ocpp.model.core.StatusNotificationRequest;

public class CoreRequest extends RequestHandler {

    public static CompletableFuture<JsonNode> sendBootNotification(){
        BootNotificationRequest request = new BootNotificationRequest("Fleischmann IoT", "testing-client");
        return sendRequest(request);
    }

    public static CompletableFuture<JsonNode> sendStatusNotification(){
        String status = ChargePoint.getInstance().getStatus();
        ChargePointErrorCode errorCode = ChargePointErrorCode.NoError;
        ChargePointStatus chargePointStatus;

        if(status.equals("Available")){
            chargePointStatus = ChargePointStatus.Available;
        }
        else if(status.equals("Preparing")){
            chargePointStatus = ChargePointStatus.Preparing;
        }
        else if(status.equals("Charging")){
            chargePointStatus = ChargePointStatus.Charging;
        }
        else if(status.equals("SuspendedEVSE")){
            chargePointStatus = ChargePointStatus.SuspendedEVSE;
        }
        else if(status.equals("SuspendedEV")){
            chargePointStatus = ChargePointStatus.SuspendedEVSE;
        }
        else if(status.equals("Finishing")){
            chargePointStatus = ChargePointStatus.Finishing;
        }
        else if(status.equals("Reserved")){
            chargePointStatus = ChargePointStatus.Reserved;
        }
        else if(status.equals("Unavailable")){
            chargePointStatus = ChargePointStatus.Unavailable;
        }
        else{
            chargePointStatus = ChargePointStatus.Faulted;
        }
        
        StatusNotificationRequest request = new StatusNotificationRequest(1, errorCode, chargePointStatus);
        request.setTimestamp(ZonedDateTime.now().plusHours(-4));
        return sendRequest(request);
    } 

    public static CompletableFuture<JsonNode> sendStartTransaction(String idTag, Integer reservationId){
        StartTransactionRequest request = new StartTransactionRequest(1, idTag, 0, ZonedDateTime.now().plusHours(-4));
        if(reservationId != null){
            request.setReservationId(reservationId);
        }
        return sendRequest(request).thenApply(result -> {
            System.out.println("StartTransaction: " + result);
            ChargePoint.getInstance().setStatus("Charging");
            sendStatusNotification(1);
            return result;
        });
    }

    // IMPLEMENTACIONES CON DELAYS

    public static void sendBootNotification(Integer delay){
        CompletableFuture.delayedExecutor(delay, TimeUnit.SECONDS).execute(() -> {
            sendBootNotification();
        });
    }

    public static void sendStatusNotification(Integer delay){
        CompletableFuture.delayedExecutor(delay, TimeUnit.SECONDS).execute(() -> {
            sendStatusNotification();
        });
    }

    public static void sendStartTransaction(String idTag, Integer reservationId, Integer delay){
        CompletableFuture.delayedExecutor(delay, TimeUnit.SECONDS).execute(() -> {
            sendStartTransaction(idTag, reservationId);
        });
    }
}
