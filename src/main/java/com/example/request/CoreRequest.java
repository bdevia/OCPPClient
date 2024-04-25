package com.example.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.profiles.CoreEvent;
import com.fasterxml.jackson.databind.JsonNode;

import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ChargePointErrorCode;
import eu.chargetime.ocpp.model.core.ChargePointStatus;

public class CoreRequest extends RequestHandler {
    
    private static ClientCoreProfile core = new ClientCoreProfile(new CoreEvent());

    public static CompletableFuture<JsonNode> sendBootNotification(){
        Request request = core.createBootNotificationRequest("Fleischmann IoT", "testing-client");
        return sendRequest(request);
    }

    public static CompletableFuture<JsonNode> sendStatusNotification(JsonNode params){
        if(params.has("status")){
            String status = params.get("status").asText();
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
            
            Request request = core.createStatusNotificationRequest(1, errorCode, chargePointStatus);
            return sendRequest(request);
        }

        else{
            List<String> list = new ArrayList<>(List.of("status"));
            return badRequest(getMessage(list));
        }
    } 
}
