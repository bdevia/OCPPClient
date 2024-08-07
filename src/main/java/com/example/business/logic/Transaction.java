package com.example.business.logic;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transaction {
    
    private final Integer id;
    private final String idTag; 
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Double comsuption;

    public Transaction(Integer id, String idTag){
        this.id = id;
        this.idTag = idTag;
        this.start = ZonedDateTime.now();
        this.end = null;
        this.comsuption = null;
    }

}
