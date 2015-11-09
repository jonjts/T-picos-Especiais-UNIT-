package br.com.unit.tec.unitplus.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.unit.tec.unitplus.adapter.NotasAdapter;
import br.com.unit.tec.unitplus.control.NotasControl;
import br.com.unit.tec.unitplus.entity.Nota;
import br.com.unit.tec.unitplus.interfaces.IConsult;

/**
 * Created by jon_j on 02/11/2015.
 */
public class NotasTask extends AsyncTask<Long, Void, NotasAdapter> {

    private IConsult iConsult;
    private Context context;

    public NotasTask(IConsult iConsult, Context context) {
        this.iConsult = iConsult;
        this.context = context;
    }

    @Override
    protected NotasAdapter doInBackground(Long... params) {
        NotasControl notasControl = new NotasControl(context);
        long userId = params[0];
        List<Nota> list = notasControl.get(userId);
        return new NotasAdapter(list);
    }

    @Override
    protected void onPostExecute(NotasAdapter notasAdapter) {
        super.onPostExecute(notasAdapter);
        if(notasAdapter != null && notasAdapter.getItemCount() > 0){
            iConsult.onConsultSuccess(notasAdapter);
        }else{
            iConsult.onConsultFail(notasAdapter);
        }
    }
}
