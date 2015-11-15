package br.com.unit.tec.unitplus.control;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.unit.tec.unitplus.entity.Horario;
import br.com.unit.tec.unitplus.persistence.DataBaseHelper;

/**
 * Created by jon_j on 01/11/2015.
 */
public class HorariosControl {

    private Dao<Horario, Long> dao;
    private Context context;

    public HorariosControl(Context context) {
        try {
            this.context = context;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            dao = dataBaseHelper.getHorarioDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Horario> get(Long idUsuario, int diaSemana){
        QueryBuilder<Horario, Long> horarioLongQueryBuilder = dao.queryBuilder();
        List<Horario> horarios = null;
        try {
            horarios = horarioLongQueryBuilder.where().eq("id_usuario", idUsuario).and().eq("dia_semana",diaSemana).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    public List<Horario> get(Long idUsuario){
        QueryBuilder<Horario, Long> horarioLongQueryBuilder = dao.queryBuilder();
        List<Horario> horarios = null;
        try {
            horarios = horarioLongQueryBuilder.where().eq("id_usuario", idUsuario).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

}
