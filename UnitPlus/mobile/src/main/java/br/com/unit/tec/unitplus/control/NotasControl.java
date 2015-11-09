package br.com.unit.tec.unitplus.control;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.unit.tec.unitplus.entity.Nota;
import br.com.unit.tec.unitplus.persistence.DataBaseHelper;

/**
 * Created by jon_j on 02/11/2015.
 */
public class NotasControl {

    private Dao<Nota, Long> dao;

    public NotasControl(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        try {
            dao = dataBaseHelper.getNotasDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Nota> get(Long idUsuario){
        try {
            QueryBuilder<Nota, Long> queryBuilder = dao.queryBuilder();
            return queryBuilder.where().eq("id_usuario",idUsuario).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
