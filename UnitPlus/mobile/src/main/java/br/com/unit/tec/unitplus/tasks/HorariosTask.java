package br.com.unit.tec.unitplus.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.unit.tec.unitplus.adapter.HorarioAdapter;
import br.com.unit.tec.unitplus.control.HorariosControl;
import br.com.unit.tec.unitplus.entity.Horario;
import br.com.unit.tec.unitplus.interfaces.IConsult;
import br.com.unit.tec.unitplus.util.Util;

/**
 * Created by jon_j on 02/11/2015.
 */
public class HorariosTask extends AsyncTask<Integer,  Void, HorarioAdapter> {

    private IConsult iConsult;
    private Context context;

    public HorariosTask(IConsult iConsult, Context context) {
        this.iConsult = iConsult;
        this.context = context;
    }

    @Override
    protected HorarioAdapter doInBackground(Integer... params) {
        Long userId = Util.getUserId(context);
        int diaSemana = params[0];

        HorariosControl horariosControl = new HorariosControl(context);
        List<Horario> list = horariosControl.get(userId, diaSemana);
        HorarioAdapter horarioAdapter = new HorarioAdapter(list,context);

        return horarioAdapter;
    }

    @Override
    protected void onPostExecute(HorarioAdapter horarioAdapter) {
        super.onPostExecute(horarioAdapter);
        if(horarioAdapter != null && !horarioAdapter.isEmpty()){
            iConsult.onConsultSuccess(horarioAdapter);
        }else{
            iConsult.onConsultFail(horarioAdapter);
        }
    }
}
