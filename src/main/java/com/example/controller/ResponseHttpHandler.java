package com.example.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.request.ErrorHandler;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseHttpHandler extends ErrorHandler{
    
    protected CompletableFuture<ResponseEntity<JsonNode>> responseHandler(CompletableFuture<JsonNode> response){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Custom-Header", "value");

        return response.thenApply(body -> {
            HttpStatus httpStatus = HttpStatus.OK;
            
            if(body.has("status")){
                String status = body.get("status").asText();
                switch (status) {
                    case "Internal Server Error":
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        break;
                    case "Fail Request":
                        httpStatus = HttpStatus.NOT_FOUND;
                        break;
                    case "Request Timeout":
                        httpStatus = HttpStatus.REQUEST_TIMEOUT;
                        break;
                }
            }

            return new ResponseEntity<>(body, headers, httpStatus);
        });
    }
}
