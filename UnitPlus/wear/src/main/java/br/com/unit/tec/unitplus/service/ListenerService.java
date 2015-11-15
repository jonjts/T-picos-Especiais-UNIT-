package br.com.unit.tec.unitplus.service;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.unit.tec.unitplus.HorarioWearActivity;
import br.com.unit.tec.unitplus.NotasWearActivity;
import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.util.Util;

/**
 * Created by jon_j on 08/11/2015.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String path = messageEvent.getPath();
        String json = new String(messageEvent.getData());
        try {
            if (path.equals(Constants.HORARIOS)) {

                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                Intent intent = new Intent(this, HorarioWearActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (path.equals(Constants.USER_NAME)) {

                JSONObject jsonObject = new JSONObject(json);
                String user_name = (String) jsonObject.get("user_name");
                Util.saveUserName(user_name, this);

            } else if (path.equals(Constants.NOTAS)) {
                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                Intent intent = new Intent(this, NotasWearActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
