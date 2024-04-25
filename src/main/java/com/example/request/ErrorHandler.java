package com.example.request;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorHandler {
    
    protected static ObjectNode errorHandler(String status, String cause){
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("timestamp", ZonedDateTime.now().toString());
        response.put("status", status);
        response.put("cause", cause);
        return response;
    } 

    protected static JsonNode internalError(String messaje){
        return errorHandler("Internal Server Error", messaje);
    }

    protected static JsonNode failRequest(String messaje){
        return errorHandler("Request Failed", messaje);
    }

    protected static JsonNode requestTimeout(){
        return errorHandler("Request Timeout", "Time limit has been reached");
    }

    protected static JsonNode failAuth(String messaje){
        return errorHandler("Authentication Failed", messaje);
    }

    protected static CompletableFuture<JsonNode> badRequest(String messaje){
        CompletableFuture<JsonNode> result = new CompletableFuture<>();
        result.complete(errorHandler("Bad Request", messaje));
        return result;
    }

    protected static String getMessage(List<String> list){
        String message = "";
        for(int i = 0; i < list.size(); i++){
            if(list.size() == 1 && i == 0){
                message = "'" + list.get(i) + "' is a mandatory parameter";
            }
            else if(i == list.size() - 1 && list.size() >= 2){
                message = message + "and '" + list.get(i) + "' are mandatory parameters";
            }
            else if(i == list.size() - 2 && list.size() >= 2){
                message = message + "'" + list.get(i) + "' ";
            }
            else{
                message = message + "'" + list.get(i) + "', "; 
            }  
        }
        return message;
    }
}