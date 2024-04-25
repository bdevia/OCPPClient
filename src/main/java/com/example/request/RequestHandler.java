package com.example.request;

import java.util.concurrent.CompletableFuture;

import com.example.OcppConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.chargetime.ocpp.CallErrorException;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.model.Request;

public class RequestHandler extends ErrorHandler {
    
    public static CompletableFuture<JsonNode> sendRequest(Request request){
        JSONClient client = OcppConnection.getInstance().getClient();
        CompletableFuture<JsonNode> resultFuture = new CompletableFuture<>();

        try{
            client.send(request).whenComplete((result, ex) -> {
                if(ex != null && ex instanceof CallErrorException){
                    CallErrorException callErrorException = (CallErrorException) ex;
                    System.out.println(callErrorException.getErrorCode());
                    resultFuture.complete(failRequest(callErrorException.getErrorCode()));
                }
                else if(ex != null){
                        System.out.println(ex.getMessage());
                        resultFuture.complete(internalError(ex.getMessage()));
                }   
                else{
                    System.out.println("Result: " + result);
                    System.out.println();
                    resultFuture.complete(new ObjectMapper().createObjectNode().put("result", result.toString()));
                }
            });
        } 
        catch(Exception e) {
            e.printStackTrace();
            resultFuture.complete(internalError(e.getMessage()));
        }
        return resultFuture;
    }
}
