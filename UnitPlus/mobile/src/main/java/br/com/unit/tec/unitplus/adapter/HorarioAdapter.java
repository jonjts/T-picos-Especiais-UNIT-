package br.com.unit.tec.unitplus.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.com.unit.tec.unitplus.R;
import br.com.unit.tec.unitplus.entity.Horario;

/**
 * Created by jon_j on 02/11/2015.
 */
public class HorarioAdapter extends BaseAdapter {

    private List<Horario> horarios;
    private Context context;
    private LayoutInflater inflater;

    private int count = 0;

    public HorarioAdapter(List<Horario> horarios, Context context) {
        this.horarios = horarios;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return horarios.size();
    }

    @Override
    public Horario getItem(int position) {
        return horarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return horarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_item, null);

        Horario horario = horarios.get(position);

        String sHora = horario.getHora() + ":" + horario.getMinutos();

        ((TextView) convertView.findViewById(R.id.txtHora)).setText(sHora);
        ((TextView) convertView.findViewById(R.id.txtLocal)).setText(horario.getLocal());
        ((TextView) convertView.findViewById(R.id.txtDiciplina)).setText(horario.getDiciplina().getNome());
        setBackground(convertView);

        return convertView;
    }

    private void setBackground(View view){
        if(count++ % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
    }
}
