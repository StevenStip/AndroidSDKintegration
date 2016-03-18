package com.example.steven.androidsdkintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textLabel = (TextView) findViewById(R.id.textLabel);
        Button startSdkButton = (Button) findViewById(R.id.StartSDK);
        Button sendEventButton = (Button) findViewById(R.id.sendEvent);
        Button upLoadEventsButton = (Button) findViewById(R.id.uploadEvents);
        Button newSessionButton = (Button) findViewById(R.id.newSession);

        startSdkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "startSDK");
                textLabel.setText("sessionId");
            }
        });

        sendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "record event");
            }
        });

        upLoadEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "upload events");
            }
        });

        newSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "new session");
                textLabel.setText("new sessionId");

            }
        });


    }
}
