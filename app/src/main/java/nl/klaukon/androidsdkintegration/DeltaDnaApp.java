package nl.klaukon.androidsdkintegration;

import android.app.Application;

import com.deltadna.android.sdk.DDNA;
import com.deltadna.android.sdk.helpers.Settings;

/**
 * Created by steven on 18/03/16.
 */
public class DeltaDnaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DDNA.initialise(new DDNA.Configuration(this, "00798022957667783424138442814546",
                        //"http://192.168.30.44:8080/collect/api",
                        //"http://192.168.30.44:8080/engage"
                        "http://collect7829tstcl.deltadna.net/collect/api",
                        "http://engage7829tstcl.deltadna.net"
                ).clientVersion("1.0")
                        .withSettings(new DDNA.SettingsModifier() {
                                          @Override
                                          public void modify(Settings settings) {
                                              settings.setBackgroundEventUpload(false);
                                              settings.setDebugMode(true);
                                          }
                                      }
                        )
        );

    }
}
