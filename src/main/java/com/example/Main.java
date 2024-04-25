package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan({"com.example.controller"})
public class Main {
    public static void main(String[] args) {
        Environment enviroment = SpringApplication.run(Main.class, args).getEnvironment();
        System.out.println("API REST running on port "  + enviroment.getProperty("server.port"));
        
        OcppConnection.getInstance();
    }
}