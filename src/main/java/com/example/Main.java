package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.example.request.CoreRequest;

@SpringBootApplication
@ComponentScan({"com.example.controller"})
public class Main {
    public static void main(String[] args) {
        Environment enviroment = SpringApplication.run(Main.class, args).getEnvironment();
        System.out.println("API REST running on port "  + enviroment.getProperty("server.port"));
        
        OcppConnection.getInstance();
        CoreRequest.sendBootNotification(3);
        CoreRequest.sendStatusNotification(6);
    }
}