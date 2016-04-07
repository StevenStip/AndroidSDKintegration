package com.example.steven.androidsdkintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deltadna.android.sdk.DDNA;
import com.deltadna.android.sdk.Engagement;
import com.deltadna.android.sdk.Event;
import com.deltadna.android.sdk.ImageMessage;
import com.deltadna.android.sdk.Product;
import com.deltadna.android.sdk.Transaction;
import com.deltadna.android.sdk.listeners.ImageMessageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textLabel = (TextView) findViewById(R.id.textLabel);
        Button startSdkButton = (Button) findViewById(R.id.StartSDK);
        Button simpleEventButton = (Button) findViewById(R.id.simpleEvent);
        Button complexEventButton = (Button) findViewById(R.id.complexEvent);
        Button upLoadEventsButton = (Button) findViewById(R.id.uploadEvents);
        Button newSessionButton = (Button) findViewById(R.id.newSession);
        Button newUserButton = (Button) findViewById(R.id.newUser);
        Button stopSDKButton = (Button) findViewById(R.id.stopSDK);
        Button simpleEngageButton = (Button) findViewById(R.id.simpleEngage);
        Button paramEngageButton = (Button) findViewById(R.id.paramEngage);
        Button imageEngageButton = (Button) findViewById(R.id.imageEngage);


        startSdkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "startSDK");
                DDNA.instance().startSdk();
                String userId = DDNA.instance().getUserId();
                Log.d(TAG, "SDK started with userID " + userId);
                textLabel.setText("SDK started with userId: " + userId);
            }
        });

        simpleEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "record simple event");
                DDNA.instance().recordEvent(
                        new Event("missionStarted")
                                .putParam("missionName", "Mission01")
                                .putParam("missionID", "M001")
                                .putParam("isTutorial", false)
                                .putParam("missionDifficulty", "EASY")
                );
                textLabel.setText("options event recorded");
            }
        });

        complexEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DDNA.instance().recordEvent(new Transaction(
                                    "IAP an in app purchase",
                                    "PURCHASE",
                                    new Product().addItem("Aragorns sword", "sword", 1)
                                            .addItem("Legolas bow", "bow", 1)
                                            .addItem("Gimilis axe", "axe", 1)
                                            .addVirtualCurrency("Golden ring", "PREMIUM", 1),
                                    new Product().setRealCurrency("USD", 100))
                    );

                    Log.d(TAG, "record complex event");

                    textLabel.setText("transaction event recorded");
            }
        });

        upLoadEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDNA.instance().upload();
                Log.d(TAG, "upload events");
            }
        });

        newSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "new session");
                String oldSessionId = DDNA.instance().getSessionId();
                DDNA.instance().newSession();
                String newSessionId = DDNA.instance().getSessionId();
                Log.d(TAG, "New session started, old sessionID = " + oldSessionId + " new sessionID: " + newSessionId);
                textLabel.setText("new sessionId:" + newSessionId);
            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "new user");
                String userId = UUID.randomUUID().toString();
                DDNA.instance().setUserId(userId);
                Log.d(TAG, "New userId set to "+userId);
                textLabel.setText("New userId set to "+userId);
            }
        });

        simpleEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Simple engage");
                DDNA.instance().requestEngagement(new Engagement("testDecisionPoint")
                                .putParam("action", "simple"),
                        new SimpleEngagementListener(textLabel));
                String userId = DDNA.instance().getUserId();
                Log.d(TAG, "SDK started with userID " + userId);
                textLabel.setText("SDK started with userId: " + userId);
            }
        });

        paramEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Param engage");
                DDNA.instance().requestEngagement(new Engagement("testDecisionPoint")
                                .putParam("action", "param"),
                        new ParamEngagementListener(textLabel));
            }
        });


        imageEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Image engage");
                DDNA.instance().requestImageMessage(new Engagement("testDecisionPoint")
                                .putParam("action", "image"),
                        new ImageMessageListener(MainActivity.this, 10) {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.w(TAG, "Requesting Engagement Failed");
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
                            protected void onPrepared(ImageMessage imageMessage) {
                                show(imageMessage);
                            }
                        });

                String userId = DDNA.instance().getUserId();
                Log.d(TAG, "SDK started with userID " + userId);
                textLabel.setText("SDK started with userId: " + userId);
            }
        });


        stopSDKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "stop SDK");
                DDNA.instance().stopSdk();
                textLabel.setText("sdk Stopped");
            }
        });
    }

    @Override
    public void onDestroy() {
        DDNA.instance().stopSdk();

        super.onDestroy();
    }


}
