package br.com.unit.tec.unitplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.unit.tec.unitplus.R;
import br.com.unit.tec.unitplus.entity.GridItem;

/**
 * Created by jon_j on 02/11/2015.
 */

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<GridItem> mItems;

    public GridAdapter(List<GridItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GridItem gridItem = mItems.get(i);
        viewHolder.txtLabel.setText(gridItem.getLabel());
        viewHolder.imgThumbnail.setImageResource(gridItem.getImage());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView txtLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.imgGrid);
            txtLabel = (TextView)itemView.findViewById(R.id.txtLabelGrid);
        }
    }
}


