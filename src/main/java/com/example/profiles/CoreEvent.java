package com.example.profiles;

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
            System.out.println(request);
            return null; 
        }

        @Override
        public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {
            System.out.println(request);
            return null; 
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
