package br.com.unit.tec.unitplus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.unit.tec.unitplus.adapter.NotasAdapter;
import br.com.unit.tec.unitplus.interfaces.IConsult;
import br.com.unit.tec.unitplus.listener.RecyclerItemClickListener;
import br.com.unit.tec.unitplus.tasks.NotasTask;
import br.com.unit.tec.unitplus.util.Util;

public class NotasActivity extends AppCompatActivity implements IConsult{

    private RecyclerView mRecyclerView;

    private View progressView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerNotas);
        mRecyclerView.setLayoutFrozen(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressView = findViewById(R.id.progress_notas);

        loadNotas();

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

    private void loadNotas(){
        showProgress(true);
        Long userId = Util.getUserId(this);
        NotasTask notasTask = new NotasTask(this,this);
        notasTask.execute(userId);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onConsultSuccess(Object object) {
        showProgress(false);
        NotasAdapter notasAdapter = (NotasAdapter) object;
        mRecyclerView.setAdapter(notasAdapter);

    }

    @Override
    public void onConsultFail(Object object) {
        showProgress(false);
    }
}
