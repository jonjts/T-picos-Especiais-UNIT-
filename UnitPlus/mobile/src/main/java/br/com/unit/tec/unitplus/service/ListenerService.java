package br.com.unit.tec.unitplus.service;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.concurrent.TimeUnit;

import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.control.UsuarioControl;
import br.com.unit.tec.unitplus.entity.Usuario;
import br.com.unit.tec.unitplus.util.Util;

/**
 * Created by jon_j on 08/11/2015.
 */
public class ListenerService extends WearableListenerService {

    String nodeId;
    private static final String USER_NAME = "/user_name";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        if (path.equals(USER_NAME)) {
            replyUserName(path);
        }
        reply("tudo certo");
        showToast(path);
    }

    private void replyUserName(String path) {
        Long userId = Util.getUserId(this);
        if (userId > 0) {
            UsuarioControl usuarioControl = new UsuarioControl(this);
            Usuario usuario = usuarioControl.get(userId);

            JSONStringer jsonStringer = new JSONStringer();

            try {
                jsonStringer.array().key(Constants.JSON_RESPONSE).object().key("path").value(path).endObject().
                        object().key("user_name").value(usuario.getNome()).endObject().endArray();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            reply(jsonStringer.toString());
        }
        reply("{user_name:null}");
    }

    @NonNull
    private String createJsonPath(String path) {
        path = "{path:" + path + "}";
        return path;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void reply(String message) {
        GoogleApiClient client = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .build();
        client.blockingConnect(100, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(client, nodeId, message, null);
        client.disconnect();
    }
}
