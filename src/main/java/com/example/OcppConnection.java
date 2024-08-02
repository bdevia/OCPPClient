package com.example;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

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
        try {
            //this.client.enableWSS(getContextSSL());
            this.client.connect("ws://172.15.200.104:8887" , null);
            this.client.addFeatureProfile(new ClientFirmwareManagementProfile(new FirmwareEvent()));
            this.client.addFeatureProfile(new ClientReservationProfile(new ReservationEvent()));
            this.client.addFeatureProfile(new ClientSmartChargingProfile(new SmartChargingEvent()));
            this.client.addFeatureProfile(new ClientLocalAuthListProfile(new AuthListEvent()));
            this.client.addFeatureProfile(new ClientRemoteTriggerProfile(new TriggerEvent()));
            System.out.println("Client connected on port 8887");
        } 
        catch(Exception e) {
            e.printStackTrace();
        }
    } 

    public static OcppConnection getInstance(){
        if(instance == null){
            instance = new OcppConnection();
        }
        return instance;
    }

    public SSLContext getContextSSL(){
        try {
            String STORETYPE = "PKCS12";
            String KEYSTORE = "/home/benja/Escritorio/ocpp-client/client-keystore.p12";
            String STOREPASSWORD = "clientpassword";
    
            KeyStore ks = KeyStore.getInstance(STORETYPE);
            FileInputStream fis = new FileInputStream(KEYSTORE);
            ks.load(fis, STOREPASSWORD.toCharArray());
            fis.close();
    
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
    
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            return sslContext;
        } 
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
