package br.com.unit.tec.unitplus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.unit.tec.unitplus.R;
import br.com.unit.tec.unitplus.entity.Nota;

/**
 * Created by jon_j on 02/11/2015.
 */

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {

    List<Nota> mItems;

    public NotasAdapter(List<Nota> mItems) {
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notas_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Nota nota = mItems.get(i);
        viewHolder.txtNomeDiciplina.setText(nota.getDiciplina().getNome());
        viewHolder.txtNota.setText(nota.getNota()+"");
        viewHolder.txtUnidade.setText(nota.getUnidade()+"");
        viewHolder.txtFaltas.setText(nota.getFaltas()+"");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtUnidade;
        public TextView txtNota;
        public TextView txtFaltas;
        public TextView txtNomeDiciplina;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUnidade = (TextView)itemView.findViewById(R.id.txtUnidade);
            txtFaltas = (TextView) itemView.findViewById(R.id.txtFaltas);
            txtNota = (TextView) itemView.findViewById(R.id.txtNota);
            txtNomeDiciplina = (TextView) itemView.findViewById(R.id.txtDiciplinaNome);
        }
    }
}


