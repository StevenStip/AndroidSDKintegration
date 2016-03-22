package com.example.steven.androidsdkintegration;

import android.app.Application;

import com.deltadna.android.sdk.DDNA;

/**
 * Created by steven on 18/03/16.
 */
public class deltaDnaApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        DDNA.initialise(new DDNA.Configuration(this, "22079697190426055695055037414340",
                "http://192.168.30.44:8080/collect/api",
                //"http://collect4792jmprb.deltadna.net/collect/api",
                "http://engage4792jmprb.deltadna.net"));
    }
}
