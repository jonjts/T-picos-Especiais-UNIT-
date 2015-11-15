package br.com.unit.tec.unitplus;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.activity.WearableActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.unit.tec.unitplus.adapter.NotasAdapter;
import br.com.unit.tec.unitplus.entity.Diciplina;
import br.com.unit.tec.unitplus.entity.Nota;

public class NotasWearActivity extends WearableActivity {

    private RecyclerView mRecyclerView;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                jsonArray = new JSONArray(extras.getString("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerNotas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNotas();

    }

    private void loadNotas() {
        ArrayList<Nota> list = new ArrayList<>();
        Nota nota;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = (JSONObject) jsonArray.get(i);
                nota = new Nota();
                nota.setId(object.getLong("id"));
                nota.setFaltas(object.getInt("faltas"));
                nota.setUnidade(object.getInt("unidade"));
                nota.setNota(object.getDouble("nota"));

                Diciplina diciplina = new Diciplina();
                JSONObject objectDiciplina = object.getJSONObject("diciplina");

                diciplina.setNome(objectDiciplina.getString("nome"));
                nota.setDiciplina(diciplina);
                list.add(nota);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            NotasAdapter notasAdapter = new NotasAdapter(list);
            mRecyclerView.setAdapter(notasAdapter);
        }
    }

}
