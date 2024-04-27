package com.example.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.CoreRequest;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/ocpp/client")
public class OcppClient extends ResponseHttpHandler{

    @GetMapping("")
    public String home(){
        return "Hello World!";
    }

    @GetMapping("/send/bootNotification")
    public CompletableFuture<ResponseEntity<JsonNode>> sendBootNotification(){
        return responseHandler(CoreRequest.sendBootNotification());
    }

    @GetMapping("/send/statusNotification")
    public CompletableFuture<ResponseEntity<JsonNode>> sendStatusNotification(){
        return responseHandler(CoreRequest.sendStatusNotification());
    }
}
