package com.example.profiles;

import eu.chargetime.ocpp.feature.profile.ClientReservationEventHandler;
import eu.chargetime.ocpp.model.reservation.CancelReservationConfirmation;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
import eu.chargetime.ocpp.model.reservation.ReserveNowConfirmation;
import eu.chargetime.ocpp.model.reservation.ReserveNowRequest;

public class ReservationEvent implements ClientReservationEventHandler {

    @Override
    public ReserveNowConfirmation handleReserveNowRequest(ReserveNowRequest var1){
        System.out.println(var1);
        return null;
    }

    @Override
    public CancelReservationConfirmation handleCancelReservationRequest(CancelReservationRequest var1){
        System.out.println(var1);
        return null;
    }
}
