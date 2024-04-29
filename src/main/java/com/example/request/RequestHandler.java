package com.example.request;

import java.util.concurrent.CompletableFuture;

import com.example.OcppConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
                    System.out.println();
                    System.out.println("Result: " + result);
                    resultFuture.complete(parseStringToJsonNode(result.toString()));
                }
            });
        } 
        catch(Exception e) {
            e.printStackTrace();
            resultFuture.complete(internalError(e.getMessage()));
        }
        return resultFuture;
    }

    public static JsonNode parseStringToJsonNode(String input) {
        int startIndex = input.indexOf("{");
        int endIndex = input.lastIndexOf("}");

        if (startIndex != -1 && endIndex != -1) {
            String jsonContent = input.substring(startIndex, endIndex + 1);
            String text = jsonContent.substring(1, jsonContent.length() - 1);
            String[] keyValuePairs = text.split(",\\s*");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.createObjectNode();

            for (String pair : keyValuePairs) {
                String[] entry = pair.split("=", 2);
                ((ObjectNode) rootNode).put(entry[0], entry[1]);
            }            
            return rootNode;
        }
        return null;
    }

}
