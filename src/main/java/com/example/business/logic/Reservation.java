package com.example.business.logic;

import java.time.ZonedDateTime;

import lombok.Getter;

@Getter
public class Reservation {
    private final Integer id;
    private final String idTag;
    private ZonedDateTime expiryDate;

    public Reservation(Integer id, String idTag, ZonedDateTime expiryDate){
        this.id = id;
        this.idTag = idTag;
        this.expiryDate = expiryDate;
    }
}
