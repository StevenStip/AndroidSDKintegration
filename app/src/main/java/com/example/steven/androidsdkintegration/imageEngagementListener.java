package com.example.steven.androidsdkintegration;

import android.app.Activity;
import android.util.Log;

import com.deltadna.android.sdk.ImageMessage;
import com.deltadna.android.sdk.listeners.ImageMessageListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by steven on 01/04/16.
 */
public class imageEngagementListener extends ImageMessageListener {
    private String TAG = "imageEngagementListener";

    public imageEngagementListener(Activity activity, int requestCode) {
        super(activity, requestCode);
    }

    @Override
    public void onSuccess(JSONObject result) {
        super.onSuccess(result);
        Log.d(TAG, "onSuccess: reached past parent");
        try {
            Log.d(TAG, result.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFailure(Throwable t) {
        Log.d(BuildConfig.VERSION_NAME, t.getMessage());
    }


    @Override
    protected void onPrepared(ImageMessage imageMessage) {
    super.show(imageMessage);
    }
}
