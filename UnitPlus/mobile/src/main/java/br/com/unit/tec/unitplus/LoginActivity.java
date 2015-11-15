package br.com.unit.tec.unitplus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.rey.material.app.Dialog;
import com.rey.material.widget.CheckBox;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.unit.tec.unitplus.constants.Constants;
import br.com.unit.tec.unitplus.entity.Usuario;
import br.com.unit.tec.unitplus.interfaces.IConsult;
import br.com.unit.tec.unitplus.tasks.LoginTask;
import br.com.unit.tec.unitplus.util.Util;


public class LoginActivity extends AppCompatActivity implements IConsult {

    private EditText mMatricula;
    private EditText mPasswordView;
    private CheckBox manterLogado;
    private View mProgressView;
    private View mLoginFormView;
    private MenuItem menuItem;

    private GoogleApiClient googleApiClient;
    private String nodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initGoogleApiClient();

        mMatricula = (EditText) findViewById(R.id.matricula);
        manterLogado = (CheckBox) findViewById(R.id.manter_logado);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    doLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.progress_view);
    }

    private void checkIfLogout() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.LOGOUT)) {
            Snackbar.make(mPasswordView, "Logout efetuado com sucesso.", Snackbar.LENGTH_LONG).show();
            sendUserNameToWear("");
        }
    }


    private void checkUserLogado() {
        Long userId = Util.getUserId(this);
        if (userId > 0) {
            showProgress(true);
            LoginTask loginTask = new LoginTask(this);
            loginTask.execute(new Usuario(userId));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        menuItem = menu.getItem(0);
        checkUserLogado();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_entrar:
                doLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogin() {
        mMatricula.setError(null);
        mPasswordView.setError(null);

        String matricula = getMatricula();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Senha inválida.");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isMatriculalValid(matricula)) {
            mMatricula.setError("Matrícula inválida.");
            focusView = mMatricula;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            executLoginTask(matricula, password);
        }
    }

    private void executLoginTask(String matricula, String password) {
        showProgress(true);
        LoginTask loginTask = new LoginTask(this);
        loginTask.execute(new Usuario(password, Long.parseLong(matricula)));
    }

    @NonNull
    private String getMatricula() {
        return mMatricula.getText().toString();
    }

    private boolean isMatriculalValid(String matricula) {
        return !matricula.isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (menuItem != null)
            menuItem.setVisible(!show);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onConsultSuccess(Object object) {
        Usuario usuario = ((Usuario) object);
        if (manterLogado.isChecked()) {
            Util.saveUserId(usuario.getLogin(), this);
            sendUserNameToWear(usuario.getNome());
        }

        throwNotification(usuario.getNome());
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PREFERENCE_ID, usuario.getLogin());
        bundle.putString(Constants.PREFERENCE_NOME, usuario.getNome());
        Intent it = new Intent(this, EntryPointActivity.class);
        it.putExtras(bundle);
        startActivity(it);
        finish();
    }

    private void throwNotification(String userName) {
        NotificationCompat.Builder notification_builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bem vindo")
                .setContentText(userName)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);


        NotificationManagerCompat notification_manager = NotificationManagerCompat.from(this);
        notification_manager.notify(0, notification_builder.build());
    }

    @Override
    public void onConsultFail(Object object) {
        showProgress(false);
        final Dialog mDialog = new Dialog(this);
        mDialog.positiveActionClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.applyStyle(R.style.SimpleDialogLight)
                .title("Falha ao fazer login.")
                .positiveAction("OK")
                .cancelable(false)
                .show();
    }

    private void initGoogleApiClient() {
        googleApiClient = getGoogleApiClient(this);
        retrieveDeviceNode();
    }

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                    googleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);

                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();

                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    nodeId = nodes.get(0).getId();
                googleApiClient.disconnect();
                checkIfLogout();
            }
        }).start();
    }

    private void sendUserNameToWear(final String userName) {
        final String json = "{\"user_name\": \"" + userName + "\"}";

        if (nodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                        googleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);

                    googleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(googleApiClient, nodeId, Constants.USER_NAME, json.getBytes());
                    googleApiClient.disconnect();
                }
            }).start();
        }


    }
}

