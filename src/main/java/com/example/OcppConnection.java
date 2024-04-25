package com.example;

import com.example.profiles.AuthListEvent;
import com.example.profiles.CoreEvent;
import com.example.profiles.FirmwareEvent;
import com.example.profiles.ReservationEvent;
import com.example.profiles.SmartChargingEvent;
import com.example.profiles.TriggerEvent;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.feature.profile.ClientFirmwareManagementProfile;
import eu.chargetime.ocpp.feature.profile.ClientLocalAuthListProfile;
import eu.chargetime.ocpp.feature.profile.ClientRemoteTriggerProfile;
import eu.chargetime.ocpp.feature.profile.ClientReservationProfile;
import eu.chargetime.ocpp.feature.profile.ClientSmartChargingProfile;
import lombok.Getter;

@Getter
public class OcppConnection {

    private static OcppConnection instance;
    private JSONClient client;

    private OcppConnection(){
        this.client = new JSONClient(new ClientCoreProfile(new CoreEvent()), "FIOT-AABBCC");
        this.client.connect("ws://192.168.41.68:8887" , null);

        this.client.addFeatureProfile(new ClientFirmwareManagementProfile(new FirmwareEvent()));
        this.client.addFeatureProfile(new ClientReservationProfile(new ReservationEvent()));
        this.client.addFeatureProfile(new ClientSmartChargingProfile(new SmartChargingEvent()));
        this.client.addFeatureProfile(new ClientLocalAuthListProfile(new AuthListEvent()));
        this.client.addFeatureProfile(new ClientRemoteTriggerProfile(new TriggerEvent()));

        System.out.println("Client connected on port 8887");
    } 

    public static OcppConnection getInstance(){
        if(instance == null){
            instance = new OcppConnection();
        }
        return instance;
    }
}
