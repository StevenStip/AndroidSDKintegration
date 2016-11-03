package nl.klaukon.androidsdkintegration;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.deltadna.android.sdk.Engagement;
import com.deltadna.android.sdk.ImageMessage;
import com.deltadna.android.sdk.listeners.EngageListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by steven on 22/03/16.
 */
public class DecisionPointOneEngagementListener implements EngageListener {
    private TextView label;
    private Activity myActivity;

    //pass in the label to give user feedback
    public DecisionPointOneEngagementListener(TextView label, Activity myActivity) {
        this.label = label;
        this.myActivity = myActivity;
    }

    String TAG = "DPOneEngListener";

    @Override
    public void onCompleted(Engagement engagement) {

        ImageMessage image = ImageMessage.create(engagement);

        if (image != null) {
            Log.d(TAG, "received image");
            image.prepare(new ImageMessage.PrepareListener() {

                @Override
                public void onPrepared(ImageMessage src) {
                    src.show(myActivity, 10);
                }

                @Override
                public void onError(Throwable cause) {
                    Log.e(TAG, cause.getMessage());
                }
            });
        }

        JSONObject result = engagement.getJson();

        if (result == null){
            Log.w(TAG, "This decision point returned null.");
            return;
        }

        try {
            Log.d(TAG, result.toString(4));
        } catch (JSONException e) {
            Log.d(TAG, "The reply does not seem to be valid JSON");
        }
        try {
            String message = result.getString("heading") + "-" + result.getString("message");
            label.setText(message);
        } catch (JSONException e) {
            Log.d(TAG, "no simple message defined");
        }
        try {
            label.setText(result.getJSONObject("parameters").get("rewardName").toString());
        } catch (JSONException e) {
            Log.d(TAG, "no rewardName in parameters");
        }

    }


    @Override
    public void onError(Throwable t) {
        Log.e(TAG, t.getMessage());

    }
}
