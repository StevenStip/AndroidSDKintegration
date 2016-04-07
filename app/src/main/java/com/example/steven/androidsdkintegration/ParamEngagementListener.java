package com.example.steven.androidsdkintegration;

import android.util.Log;
import android.widget.TextView;

import com.deltadna.android.sdk.listeners.EngageListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by steven on 22/03/16.
 */
public class ParamEngagementListener implements EngageListener {
    private TextView label;

    //pass in the label to give user feedback
    public ParamEngagementListener(TextView label){
        this.label = label;
    }

    String TAG = "ParamEngagementListener";
    public void onSuccess(JSONObject result){
        try {
            label.setText(result.getJSONObject("parameters").toString());
            Log.d(TAG, result.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onFailure(Throwable e){
        Log.e(TAG,e.getMessage());
    }
}
