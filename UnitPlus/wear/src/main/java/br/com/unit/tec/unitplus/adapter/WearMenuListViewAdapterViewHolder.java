package br.com.unit.tec.unitplus.adapter;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.unit.tec.unitplus.R;

/**
 * Created by florentchampigny on 01/04/15.
 */
public class WearMenuListViewAdapterViewHolder extends WearableListView.ViewHolder {
    public TextView textView;
    public ImageButton imageButton;

    public WearMenuListViewAdapterViewHolder(View itemView) {
        super(itemView);
        // find the text view within the custom item's layout
        textView = (TextView) itemView.findViewById(R.id.text);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
    }
}
