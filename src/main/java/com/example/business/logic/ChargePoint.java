package com.example.business.logic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargePoint {
    
    private static ChargePoint instance;
    private String status;
    private Integer connector;
    private String idTag;
    private Integer reservationId;
    private Integer transactionId;

    private ChargePoint(String status, Integer connector){
        this.status = status;
        this.connector = connector;
        this.idTag = null;
        this.reservationId = null;
        this.transactionId = null;
    }

    public static ChargePoint getInstance(){
        if(instance == null){
            instance = new ChargePoint("Available", 1);
        }
        return instance;
    }
}
