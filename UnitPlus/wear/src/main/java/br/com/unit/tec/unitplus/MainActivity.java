package br.com.unit.tec.unitplus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.rey.material.widget.SnackBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.unit.tec.unitplus.adapter.ElementGridPagerAdapter;
import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.entity.Element;
import br.com.unit.tec.unitplus.wearmenu.WearMenu;

public class MainActivity extends Activity implements MessageApi.MessageListener{

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final long CONNECTION_TIME_OUT_MS = 100;


    private GridViewPager pager;
    private DotsPageIndicator dotsPageIndicator;

    //la liste des éléments à afficher
    private List<Element> elementList;

    private GoogleApiClient googleApiClient;
    private String nodeId;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGoogleApiClient();

        pager = (GridViewPager) findViewById(R.id.pager);
        dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        elementList = creerListElements();
        pager.setAdapter(new ElementGridPagerAdapter(this, elementList, getFragmentManager()));


        final WearMenu wearMenu = (WearMenu) findViewById(R.id.wear_menu);
        wearMenu.setMenuElements(
                new String[]{
                        "Notas",
                        "Horários",
                },
                new Drawable[]{
                        getResources().getDrawable(R.drawable.ic_notas, null),
                        getResources().getDrawable(R.drawable.ic_horario, null),
                }
        );
        wearMenu.setWearMenuListener(new WearMenu.WearMenuListener() {
            @Override
            public void onWearMenuListClicked(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        getUserName();
                        break;
                }
            }
        });

    }

    private void initGoogleApiClient() {
        googleApiClient = getGoogleApiClient(this);
        retrieveDeviceNode();
    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                    googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);

                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();

                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    nodeId = nodes.get(0).getId();

                Log.v(LOG_TAG, "Node ID of phone: " + nodeId);

                googleApiClient.disconnect();
            }
        }).start();
    }


    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    private void getUserName() {
        if (nodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                        googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);

                    Wearable.MessageApi.sendMessage(googleApiClient, nodeId, Constants.USER_NAME, null).await();
                    googleApiClient.disconnect();
                }
            }).start();
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        SnackBar.make(this).show();
        Log.v(LOG_TAG, "In onMessageReceived()");

        if (messageEvent.getPath().equals("/wear")) {
            Log.v(LOG_TAG, "Success!");
        }
    }

    private List<Element> creerListElements() {
        List<Element> list = new ArrayList<>();

        list.add(new Element("Bem Vindo", "", Color.parseColor("#F44336")));

        return list;
    }

}
