package br.com.unit.tec.unitplus.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.unit.tec.unitplus.HorarioWearFragment;
import br.com.unit.tec.unitplus.R;
import br.com.unit.tec.unitplus.entity.Diciplina;
import br.com.unit.tec.unitplus.entity.Horario;
import github.cesarferreira.jsonify.JSONify;

public class HorariosGridPagerAdapter extends FragmentGridPagerAdapter {

    private Context mContext;
    private List<Row> mRows;
    private Drawable mBackgroundDrawable;

    public HorariosGridPagerAdapter(Context context, JSONArray elements, FragmentManager fm) {
        super(fm);

        this.mContext = context;
        this.mRows = new ArrayList<>();

        this.mBackgroundDrawable = mContext.getResources().getDrawable(R.drawable.wearmenu_background);

        HorarioWearFragment[] fragments = new HorarioWearFragment[6];

        int count = 0;
        for (int i = 2; i <= 7; i++) {
            fragments[count++] = HorarioWearFragment.newInstance(i, receiveHorarios(elements, i));
        }
        mRows.add(new Row(fragments));
    }

    private String receiveHorarios(JSONArray jsonArray, int diaSemana)  {
        List<Horario> list = new ArrayList<>();
        Horario horario;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonHorario = (JSONObject) jsonArray.get(i);
                if (jsonHorario.getInt("diaSemana") == diaSemana) {
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JSONify.from(list);
    }


    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }


    @Override
    public Drawable getBackgroundForRow(final int row) {
        return mBackgroundDrawable;
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        return getBackgroundForRow(row);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }


    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }

        Fragment getColumn(int i) {
            return columns.get(i);
        }

        public int getColumnCount() {
            return columns.size();
        }
    }

}