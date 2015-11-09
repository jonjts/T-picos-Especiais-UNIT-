package br.com.unit.tec.unitplus;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.unit.tec.unitplus.constants.Constants;

/**
 * Created by jon_j on 08/11/2015.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        final String message = new String(messageEvent.getData());
        try {
            getPath(new JSONArray(messageEvent.getPath()));
            if (messageEvent.getPath().equals(Constants.USER_NAME)) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String user_name = (String) jsonObject.get("user_name");
                    Log.v(" ", user_name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getPath(JSONArray jsonArray) {
        try {
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            return (String) jsonObject.get("path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
