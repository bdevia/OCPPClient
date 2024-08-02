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
    private Transaction transaction;
    private Boolean flag;
    private String statusFirmware;

    private ChargePoint(String status, Integer connector){
        this.status = status;
        this.connector = connector;
        this.reservation = null;
        this.transaction = null;
        this.flag = false;
        this.statusFirmware = "Idle";
    }

    public static ChargePoint getInstance(){
        if(instance == null){
            instance = new ChargePoint("Available", 1);
        }
        return instance;
    }

    public static void expireReservation(ZonedDateTime expiryDate){
        ZonedDateTime now = ZonedDateTime.now().plusHours(-4);
        Duration duration = Duration.between(now, expiryDate);
        long secondsRemaining = duration.getSeconds();
        System.out.println(secondsRemaining);

        CompletableFuture.delayedExecutor(secondsRemaining, TimeUnit.SECONDS)
        .execute(() -> {
            if(instance.flag){
                System.out.println("expira");
                instance.setStatus("Available");
                instance.setReservation(null);
                CoreRequest.sendStatusNotification();
            }
        });
    }

}
