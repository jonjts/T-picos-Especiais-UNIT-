package br.com.unit.tec.unitplus;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

import br.com.unit.tec.unitplus.adapter.HorariosGridPagerAdapter;

public class HorarioWearActivity extends WearableActivity {

    private GridViewPager pager;
    private DotsPageIndicator dotsPageIndicator;

    private JSONArray elementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            try {
                elementList = new JSONArray(extras.getString("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        pager = (GridViewPager) findViewById(R.id.pager_horario);
        dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator_horario);
        dotsPageIndicator.setPager(pager);

        pager.setAdapter(new HorariosGridPagerAdapter(this, elementList, getFragmentManager()));

        Calendar calendar = Calendar.getInstance();
        int diaAtual = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        pager.setCurrentItem(diaAtual,0);

    }
}
