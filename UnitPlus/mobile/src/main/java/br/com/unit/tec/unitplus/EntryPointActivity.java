package br.com.unit.tec.unitplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.util.ArrayList;

import br.com.unit.tec.unitplus.adapter.GridAdapter;
import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.entity.GridItem;
import br.com.unit.tec.unitplus.listener.RecyclerItemClickListener;
import br.com.unit.tec.unitplus.util.GridSpacingItemDecoration;
import br.com.unit.tec.unitplus.util.Util;

public class EntryPointActivity extends AppCompatActivity {

    private String userName;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        userName = extras.getString(Constants.PREFERENCE_NOME);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setPadding(8, 8, 20, 8);
        mRecyclerView.setLayoutFrozen(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        onHorarioClicked(view);
                        break;
                    case 1:
                        onNotasClicked(view);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spanCount = 3;
        int spacing = 10;
        boolean includeEdge = false;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        mAdapter = createGridLis();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void onNotasClicked(View view) {
        Intent intent = new Intent(this, NotasActivity.class);
        startActivity(intent);
    }

    private GridAdapter createGridLis() {
        ArrayList<GridItem> list = new ArrayList<>();
        GridItem gridItem = new GridItem(GridItem.ITEM_HORARIOS, R.drawable.ic_horario, "Horário");
        list.add(gridItem);

        gridItem = new GridItem(GridItem.ITEM_NOTAS, R.drawable.ic_notas, "Notas");
        list.add(gridItem);

        GridAdapter gridAdapter = new GridAdapter(list);
        return gridAdapter;
    }


    public void onLogoutClicked(View view) {
        Dialog.Builder mDialog = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                Util.clearSession(EntryPointActivity.this);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.LOGOUT, true);
                Intent intent = new Intent(EntryPointActivity.this, LoginActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder) mDialog).message("Deseja fazer logout em " + userName + "?")
                .title("Logout")
                .positiveAction("Sim")
                .negativeAction("Não");
        DialogFragment fragment = DialogFragment.newInstance(mDialog);
        fragment.show(getSupportFragmentManager(), null);


    }

    public void onHorarioClicked(View view) {
        Intent intent = new Intent(this, HorarioActivity.class);
        startActivity(intent);
    }
}
