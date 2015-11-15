package br.com.unit.tec.unitplus.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.control.HorariosControl;
import br.com.unit.tec.unitplus.control.NotasControl;
import br.com.unit.tec.unitplus.entity.Horario;
import br.com.unit.tec.unitplus.entity.Nota;
import br.com.unit.tec.unitplus.util.Util;
import github.cesarferreira.jsonify.JSONify;

/**
 * Created by jon_j on 08/11/2015.
 */
public class ListenerService extends WearableListenerService {

    private String nodeId;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        if (path.equals(Constants.HORARIOS)) {
            Long userId = Util.getUserId(this);
            HorariosControl horariosControl = new HorariosControl(this);
            List<Horario> horarios = horariosControl.get(userId);
            String json = JSONify.from(horarios);
            reply(path, json);
        } else if (path.equals(Constants.NOTAS)) {
            Long userId = Util.getUserId(this);
            NotasControl notasControl = new NotasControl(this);
            List<Nota> notas = notasControl.get(userId);
            String json = JSONify.from(notas);
            reply(path, json);
        }
    }


    private void reply(String path, String message) {
        GoogleApiClient client = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .build();
        client.blockingConnect(100, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(client, nodeId, path, message.getBytes());
        client.disconnect();
    }
}
