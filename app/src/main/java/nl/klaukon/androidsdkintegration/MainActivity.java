package nl.klaukon.androidsdkintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.deltadna.android.sdk.DDNA;
import com.deltadna.android.sdk.Engagement;
import com.deltadna.android.sdk.Event;
import com.deltadna.android.sdk.EventActionHandler;
import com.deltadna.android.sdk.Product;
import com.deltadna.android.sdk.Transaction;
import com.deltadna.android.sdk.ads.DDNASmartAds;
import com.deltadna.android.sdk.ads.InterstitialAd;
import com.deltadna.android.sdk.ads.RewardedAd;
import com.deltadna.android.sdk.helpers.Settings;
import com.deltadna.android.sdk.notifications.DDNANotifications;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private static String lastAdType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DDNA.instance().startSdk();

        setContentView(nl.klaukon.androidsdkintegration.R.layout.activity_main);
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
        Button smartAdsButton = (Button) findViewById(R.id.smartAds);
        Button notificationsRegisterButton = (Button) findViewById(R.id.notifications);
        final CheckBox consentSwitch = (CheckBox) findViewById(R.id.adConsent);
        ;


        consentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DDNASmartAds.instance().getSettings()
                        .setUserConsent(isChecked);
                DDNA.instance().newSession();

            }
        });


        smartAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastAdType == "interstitial") {
                    Log.d(TAG, "rewarded ad show");

                    RewardedAd reward = RewardedAd.create(new Engagement("ads"));
                    if (reward != null) {
                        reward.show();
                    }

                    lastAdType = "rewarded";
                } else {
                    Log.d(TAG, "interstitial ad show");
                    InterstitialAd interstitial = InterstitialAd.create();
                    if (interstitial != null) {
                        interstitial.show();
                    }
                    lastAdType = "interstitial";
                }
            }
        });


        startSdkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "startSDK");
                DDNA.instance().startSdk();
                String userId = DDNA.instance().getUserId();
                DDNA.instance().getSettings().setDebugMode(true);

                Log.d(TAG, "SDK started with userID " + userId);
                textLabel.setText("SDK started with userId: " + userId);
            }
        });

        notificationsRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "notificationsRegisterButton");
                DDNANotifications.register(MainActivity.this);
                String userId = DDNA.instance().getUserId();
                Log.d(TAG, "SDK retrieved registrationID: " + DDNA.instance().getRegistrationId());
                textLabel.setText("SDK retrieved registrationID: " + DDNA.instance().getRegistrationId());
            }
        });


        simpleEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "record simple event");
                DDNA.instance().recordEvent(
                        new Event("options")
                                .putParam("action", "disable")
                                .putParam("option", "music")
                ).add(new EventActionHandler.ImageMessageHandler(imageMessage -> {
                    // the image message is already prepared so it will show instantly
                    imageMessage.show(MainActivity.this, 0);
                })).run();
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


                int x = Product.convertCurrency(DDNA.instance(), "USD", 8.56f);
                Log.e(TAG, "CONVERTEDTHINGY USD: " + x);

                x = Product.convertCurrency(DDNA.instance(), "JPY", 105f);
                Log.e(TAG, "CONVERTEDTHINGY JPY: " + x);

                x = Product.convertCurrency(DDNA.instance(), "IDR", 1050.00f);
                Log.e(TAG, "CONVERTEDTHINGY IDR: " + x);

                x = Product.convertCurrency(DDNA.instance(), "KWD", 654.321f);
                Log.e(TAG, "CONVERTEDTHINGY KWD: " + x);

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
                DDNASmartAds.instance().getSettings()
                        .setUserConsent(consentSwitch.isChecked());

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
                DDNA.instance().stopSdk();
                DDNA.instance().clearPersistentData();
                DDNA.instance().startSdk(userId);
                Log.d(TAG, "New userId set to " + userId);
                textLabel.setText("New userId set to " + userId);
            }
        });

        simpleEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Simple engage");
                DDNA.instance().requestEngagement(new Engagement("testDecisionPoint")
                                .putParam("action", "simple"),
                        new DecisionPointOneEngagementListener(textLabel, null));
            }
        });

        assert paramEngageButton != null;
        paramEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Param engage");
                DDNA.instance().requestEngagement(new Engagement("testDecisionPoint")
                                .putParam("action", "param"),
                        new DecisionPointOneEngagementListener(textLabel, null));
            }
        });


        imageEngageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Image engage");
                DDNA.instance().requestEngagement(new Engagement("testDecisionPoint")
                                .putParam("action", "image"),
                        new DecisionPointOneEngagementListener(textLabel, MainActivity.this));
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
