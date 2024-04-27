package com.example.business.logic;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.request.CoreRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargePoint {
    
    private static ChargePoint instance;
    private String status;
    private Integer connector;
    private Reservation reservation;
    private Integer transactionId;

    private ChargePoint(String status, Integer connector){
        this.status = status;
        this.connector = connector;
        this.reservation = null;
        this.transactionId = null;
    }

    public static ChargePoint getInstance(){
        if(instance == null){
            instance = new ChargePoint("Available", 1);
        }
        return instance;
    }

    public static void expireReservation(ZonedDateTime expiryDate){
        ZonedDateTime now = ZonedDateTime.now().plusHours(-4);
        now.plusMinutes(-4);
        Duration duration = Duration.between(now, expiryDate);
        long secondsRemaining = duration.getSeconds();

        CompletableFuture.delayedExecutor(secondsRemaining, TimeUnit.SECONDS)
        .execute(() -> {
            System.out.println("expira");
            instance.setStatus("Available");
            instance.setReservation(null);
            CoreRequest.sendStatusNotification();
        });
    }
}
