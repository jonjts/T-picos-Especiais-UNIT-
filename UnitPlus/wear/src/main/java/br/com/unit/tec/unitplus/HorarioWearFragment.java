package br.com.unit.tec.unitplus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.unit.tec.unitplus.adapter.HorarioAdapter;
import br.com.unit.tec.unitplus.entity.Diciplina;
import br.com.unit.tec.unitplus.entity.Horario;


public class HorarioWearFragment extends Fragment {

    private static final String ARG_DIA_SEMANA = "DIA_SEMANA";

    private int diaSemana;
    private String json;
    private ListView listHorarios;
    private TextView txtDiaSemana;


    public static HorarioWearFragment newInstance(int diaSemana, String json) {
        HorarioWearFragment fragment = new HorarioWearFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DIA_SEMANA, diaSemana);
        args.putString("json", json);
        fragment.setArguments(args);
        return fragment;
    }

    public HorarioWearFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            diaSemana = getArguments().getInt(ARG_DIA_SEMANA);
            json = getArguments().getString("json");
        }
    }

    private void receiveHorarios(JSONArray jsonArray) throws Exception {
        List<Horario> list = new ArrayList<>();
        Horario horario;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonHorario = (JSONObject) jsonArray.get(i);
            horario = new Horario();

            horario.setMinutos(jsonHorario.getInt("minutos"));
            horario.setLocal(jsonHorario.getString("local"));
            horario.setDiaSemana(jsonHorario.getInt("diaSemana"));
            horario.setHora(jsonHorario.getInt("hora"));
            horario.setId(jsonHorario.getLong("id"));

            Diciplina diciplina = new Diciplina();
            JSONObject jsonDiciplina = jsonHorario.getJSONObject("diciplina");

            diciplina.setId(jsonDiciplina.getLong("id"));
            diciplina.setNome(jsonDiciplina.getString("nome"));

            horario.setDiciplina(diciplina);
            list.add(horario);
        }
        if (list.isEmpty()) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.empty_list, null);
            ((ViewGroup) listHorarios.getParent()).addView(view);
            listHorarios.setEmptyView(view);
            return;
        }
        HorarioAdapter horarioAdapter = new HorarioAdapter(list, getActivity());
        listHorarios.setAdapter(horarioAdapter);
        listHorarios.setLayoutAnimation(animation());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_horario, container, false);
        listHorarios = (ListView) inflate.findViewById(R.id.listHorarios);
        txtDiaSemana = (TextView) inflate.findViewById(R.id.txtDiaSemana);

        loadHorarios();

        return inflate;
    }

    private void loadHorarios() {
        try {
            receiveHorarios(new JSONArray(json));
            txtDiaSemana.setText(getDiaSemana());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private LayoutAnimationController animation() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        set.addAnimation(animation);

        return new LayoutAnimationController(set, 0.5f);
    }

    public String getDiaSemana() {
        String diaSemana = null;
        switch (this.diaSemana) {
            case 1: {
                diaSemana = "Domingo";
                break;
            }
            case 2: {
                diaSemana = "Segunda";
                break;
            }
            case 3: {
                diaSemana = "Terça";
                break;
            }
            case 4: {
                diaSemana = "Quarta";
                break;
            }
            case 5: {
                diaSemana = "Quinta";
                break;
            }
            case 6: {
                diaSemana = "Sexta";
                break;
            }
            case 7: {
                diaSemana = "Sábado";
                break;
            }

        }
        return diaSemana;

    }
}
