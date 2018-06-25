package nl.klaukon.androidsdkintegration;

import android.app.Application;

import com.deltadna.android.sdk.DDNA;
import com.deltadna.android.sdk.ads.DDNASmartAds;
import com.deltadna.android.sdk.helpers.Settings;

/**
 * Created by steven on 18/03/16.
 */
public class DeltaDnaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DDNA.initialise(new DDNA.Configuration(this,
                        "00798022957667783424138442814546",
                        "https://collect7817gldtr.deltadna.net/collect/api",
                        "https://stage-engage.deltadna.net"
                ).clientVersion("1.0"));
        DDNA.instance().getSettings().setDebugMode(true);

        DDNA.instance().getUserId();
        DDNASmartAds.initialise(new DDNASmartAds.Configuration(this));
    }
}
