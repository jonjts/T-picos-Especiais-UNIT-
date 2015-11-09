package br.com.unit.tec.unitplus.tasks;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import br.com.unit.tec.unitplus.control.UsuarioControl;
import br.com.unit.tec.unitplus.entity.Usuario;
import br.com.unit.tec.unitplus.interfaces.IConsult;

/**
 * Created by jon_j on 23/10/2015.
 */
public class LoginTask extends AsyncTask<Usuario, Void, Usuario> {


    private IConsult iConsult;

    public LoginTask(IConsult iConsult) {
        this.iConsult = iConsult;
    }


    @Override
    protected Usuario doInBackground(Usuario... params) {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UsuarioControl usuarioControl = new UsuarioControl(((AppCompatActivity) iConsult));
        Usuario param = params[0];
        if (param.getLogin() != null) {
            return usuarioControl.get(param.getLogin());
        } else {
            return usuarioControl.get(param.getLogin(), param.getSenha());
        }
    }

    @Override
    protected void onPostExecute(Usuario result) {
        super.onPostExecute(result);
        if (result == null) {
            iConsult.onConsultFail(result);
        } else {
            iConsult.onConsultSuccess(result);
        }
    }
}
