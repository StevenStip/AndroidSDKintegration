package com.example.steven.androidsdkintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deltadna.android.sdk.DDNA;
import com.deltadna.android.sdk.Event;
import com.deltadna.android.sdk.Product;
import com.deltadna.android.sdk.Transaction;

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
                        new Event("options")
                                .putParam("action", "click")
                                .putParam("option", "uselessButton")
                );
                textLabel.setText("options event recorded");
            }
        });

        complexEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "record complex event");
                DDNA.instance().recordEvent(new Transaction(
                                "IAP an in app purchase",
                                "PURCHASE",
                                new Product().addItem("Aragorns sword", "sword", 1)
                                            .addItem("Legolas bow","bow",1)
                                            .addItem("Gimilis axe","axe",1)
                                            .addVirtualCurrency("Golden ring","PREMIUM",1),
                                new Product().setRealCurrency("USD", 100))
                );
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
    }

    @Override
    public void onDestroy() {
        DDNA.instance().stopSdk();

        super.onDestroy();
    }


}
