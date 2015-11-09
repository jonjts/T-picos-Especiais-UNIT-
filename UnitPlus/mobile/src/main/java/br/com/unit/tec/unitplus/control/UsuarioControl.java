package br.com.unit.tec.unitplus.control;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

import br.com.unit.tec.unitplus.entity.Usuario;
import br.com.unit.tec.unitplus.persistence.DataBaseHelper;

/**
 * Created by jon_j on 28/10/2015.
 */
public class UsuarioControl {

    private Dao<Usuario, Long> dao;

    public UsuarioControl(Context context) {
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            dao = dataBaseHelper.getUsuarioDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario get(Long id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario get(Long login, String senha){
        try {
            QueryBuilder<Usuario, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq("login",login).and().eq("senha", senha);
            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
