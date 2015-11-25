package br.com.unit.tec.unitplus;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.rey.material.widget.TabPageIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import br.com.unit.tec.unitplus.util.CustomViewPager;

public class HorarioActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private TabPageIndicator tpi;

    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tpi = (TabPageIndicator) findViewById(R.id.main_tpi);

        mPagerAdapter = new PagerAdapter(this, getSupportFragmentManager());

        mViewPager = (CustomViewPager) findViewById(R.id.main_vp);
        mViewPager.setAdapter(mPagerAdapter);
        tpi.setViewPager(mViewPager);

        Calendar calendar = Calendar.getInstance();
        mViewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_WEEK)-2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private static class PagerAdapter extends FragmentStatePagerAdapter {

        private final Context context;
        private Calendar calendar;

        public PagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
            calendar = Calendar.getInstance(Locale.getDefault());
        }

        @Override
        public Fragment getItem(int position) {
            return HorarioFragment.newInstance(position + 2);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SEGUNDA-FEIRA";
                case 1:
                    return "TERÇA-FEIRA";
                case 2:
                    return "QUARTA-FEIRA";
                case 3:
                    return "QUINTA-FEIRA";
                case 4:
                    return "SEXTA-FEIRA";
                case 5:
                    return "SÁBADO";
            }
            return null;
        }

        @Override
        public int getCount() {
            return calendar.getMaximum(Calendar.DAY_OF_WEEK) - 1;
        }

    }


}
