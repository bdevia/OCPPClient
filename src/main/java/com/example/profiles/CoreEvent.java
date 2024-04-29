package com.example.profiles;

import com.example.business.logic.ChargePoint;
import com.example.business.logic.Reservation;
import com.example.business.logic.Transaction;
import com.example.request.CoreRequest;

import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.model.core.AvailabilityStatus;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityConfirmation;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;
import eu.chargetime.ocpp.model.core.ChangeConfigurationConfirmation;
import eu.chargetime.ocpp.model.core.ChangeConfigurationRequest;
import eu.chargetime.ocpp.model.core.ClearCacheConfirmation;
import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.core.DataTransferConfirmation;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.core.GetConfigurationConfirmation;
import eu.chargetime.ocpp.model.core.GetConfigurationRequest;
import eu.chargetime.ocpp.model.core.RemoteStartStopStatus;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionConfirmation;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionConfirmation;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest;
import eu.chargetime.ocpp.model.core.ResetConfirmation;
import eu.chargetime.ocpp.model.core.ResetRequest;
import eu.chargetime.ocpp.model.core.UnlockConnectorConfirmation;
import eu.chargetime.ocpp.model.core.UnlockConnectorRequest;

public class CoreEvent implements ClientCoreEventHandler{
    
        @Override
        public ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {
            System.out.println(request);
            return new ChangeAvailabilityConfirmation(AvailabilityStatus.Accepted);
        }

        @Override
        public GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {
            System.out.println(request);
            return null;
        }

        @Override
        public ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {
            System.out.println(request);
            return null;
        }

        @Override
        public ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {
            System.out.println(request);
            return null;
        }

        @Override
        public DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {
            System.out.println(request);
            return null;
        }

        @Override
        public RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request){
            Reservation reservation = ChargePoint.getInstance().getReservation();
            String status = ChargePoint.getInstance().getStatus();

            if(status.equals("Available") || (status.equals("Reserved") && reservation.getIdTag().equals(request.getIdTag()))){
                ChargePoint.getInstance().setStatus("Preparing");
                ChargePoint.getInstance().setReservation(null);
                ChargePoint.getInstance().setFlag(false);
                CoreRequest.sendStatusNotification(3);

                if(reservation != null){
                    CoreRequest.sendStartTransaction(request.getIdTag(), reservation.getId(), 30);
                }
                else{
                    CoreRequest.sendStartTransaction(request.getIdTag(), null, 30);
                }

                return new RemoteStartTransactionConfirmation(RemoteStartStopStatus.Accepted);
            }
            else{
                return new RemoteStartTransactionConfirmation(RemoteStartStopStatus.Rejected);
            }
        }

        @Override
        public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {
            System.out.println(request);
            Transaction transaction = ChargePoint.getInstance().getTransaction();
            if(transaction != null && transaction.getId().equals(request.getTransactionId())){
                ChargePoint.getInstance().setStatus("Finishing");
                CoreRequest.sendStatusNotification(1);
                CoreRequest.sendStopTransaction(20);
                return new RemoteStopTransactionConfirmation(RemoteStartStopStatus.Accepted);
            }
            else{
                return new RemoteStopTransactionConfirmation(RemoteStartStopStatus.Rejected);
            } 
        }

        @Override
        public ResetConfirmation handleResetRequest(ResetRequest request) {
            System.out.println(request);

            return null;
        }

        @Override
        public UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {
            System.out.println(request);
            return null; 
        }

}
