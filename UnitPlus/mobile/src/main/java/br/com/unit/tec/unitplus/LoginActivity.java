package br.com.unit.tec.unitplus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import com.rey.material.app.Dialog;
import com.rey.material.widget.CheckBox;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        checkIfLogout();

    }

    private void checkIfLogout() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.LOGOUT)) {
            Snackbar.make(mPasswordView, "Logout efetuado com sucesso.", Snackbar.LENGTH_LONG).show();
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
        }

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.PREFERENCE_ID, usuario.getLogin());
        bundle.putString(Constants.PREFERENCE_NOME, usuario.getNome());
        Intent it = new Intent(this, EntryPointActivity.class);
        it.putExtras(bundle);
        startActivity(it);
        finish();
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
}

