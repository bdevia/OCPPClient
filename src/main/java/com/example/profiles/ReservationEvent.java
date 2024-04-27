package com.example.profiles;

import com.example.business.logic.ChargePoint;
import com.example.business.logic.Reservation;
import com.example.request.CoreRequest;

import eu.chargetime.ocpp.feature.profile.ClientReservationEventHandler;
import eu.chargetime.ocpp.model.reservation.CancelReservationConfirmation;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
import eu.chargetime.ocpp.model.reservation.CancelReservationStatus;
import eu.chargetime.ocpp.model.reservation.ReservationStatus;
import eu.chargetime.ocpp.model.reservation.ReserveNowConfirmation;
import eu.chargetime.ocpp.model.reservation.ReserveNowRequest;

public class ReservationEvent implements ClientReservationEventHandler {

    @Override
    public ReserveNowConfirmation handleReserveNowRequest(ReserveNowRequest request){
        System.out.println(request);
        if(ChargePoint.getInstance().getReservation() == null){
            ChargePoint.getInstance().setStatus("Reserved");
            ChargePoint.getInstance().setReservation(new Reservation(request.getReservationId(), request.getIdTag(), request.getExpiryDate()));
            ChargePoint.expireReservation(request.getExpiryDate());
            CoreRequest.sendStatusNotification(3);
            return new ReserveNowConfirmation(ReservationStatus.Accepted);
        }
        else{
            return new ReserveNowConfirmation(ReservationStatus.Rejected);
        }
    }

    @Override
    public CancelReservationConfirmation handleCancelReservationRequest(CancelReservationRequest request){
        System.out.println(request);
        Reservation reservation = ChargePoint.getInstance().getReservation();
        if(reservation != null && reservation.getId().equals(request.getReservationId())){
            ChargePoint.getInstance().setStatus("Available");
            ChargePoint.getInstance().setReservation(null);
            CoreRequest.sendStatusNotification(3);
            return new CancelReservationConfirmation(CancelReservationStatus.Accepted);
        }
        else{
            return new CancelReservationConfirmation(CancelReservationStatus.Rejected);
        }
    }
}
