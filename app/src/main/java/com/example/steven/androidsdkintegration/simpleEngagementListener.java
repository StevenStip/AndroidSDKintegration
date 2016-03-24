package com.example.steven.androidsdkintegration;

import android.util.Log;

import com.deltadna.android.sdk.listeners.EngageListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by steven on 22/03/16.
 */
public class simpleEngagementListener implements EngageListener {
    String TAG = "simpleEngagementListener";
    public void onSuccess(JSONObject result){
        try {
            Log.d(TAG, result.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onFailure(Throwable e){
        Log.i(TAG,e.getMessage());
    }

}
