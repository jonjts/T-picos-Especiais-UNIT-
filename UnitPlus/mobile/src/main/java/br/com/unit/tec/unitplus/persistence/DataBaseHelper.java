package br.com.unit.tec.unitplus.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import br.com.unit.tec.unitplus.entity.Diciplina;
import br.com.unit.tec.unitplus.entity.Horario;
import br.com.unit.tec.unitplus.entity.Nota;
import br.com.unit.tec.unitplus.entity.Usuario;

/**
 * Created by jon_j on 28/10/2015.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {


    public static final String DATABASE_NAME = "unitPlus.db";

    private static final int DATABASE_VERSION = 21;

    private Dao<Usuario, Long> usuarioDao;
    private Dao<Diciplina, Long> diciplinaDao;
    private Dao<Horario, Long> horarioDao;
    private Dao<Nota, Long> notasDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, Diciplina.class);
            TableUtils.createTable(connectionSource, Horario.class);
            TableUtils.createTable(connectionSource, Nota.class);
            createJonas();
            createDiciplina();
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Horario.class, true);
            TableUtils.dropTable(connectionSource, Diciplina.class, true);
            TableUtils.dropTable(connectionSource, Usuario.class, true);
            TableUtils.dropTable(connectionSource, Nota.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    private void createJonas() throws SQLException {
        Usuario jonas = new Usuario("Jonas Tavares", "tavares", 2091140052l);
        getUsuarioDAO().create(jonas);
    }

    private void createDiciplina() throws SQLException {
        Dao<Diciplina, Long> diciplinaDao = getDiciplinaDao();
        Dao<Horario, Long> horarioDao = getHorarioDao();

        Usuario usuario = new Usuario(2091140052l);

        //BANCO
        Diciplina diciplina = new Diciplina(1l);
        diciplina.setNome("ADMINISTRAÇÃO DE BANCO DE DADOS");
        diciplinaDao.create(diciplina);

        createHorario(usuario, diciplina, 18, 45, Calendar.MONDAY, "BLOCO A - SALA 2");

        createHorario(usuario, diciplina, 19, 35, Calendar.MONDAY, "BLOCO A - SALA 2");

        createNota(usuario, diciplina, 1, 7.2, 4);

        //COPILADORES
        diciplina = new Diciplina(2l);
        diciplina.setNome("COPILADORES");
        diciplinaDao.create(diciplina);

        createHorario(usuario, diciplina, 18, 45, Calendar.TUESDAY, "BLOCO A - SALA 29");

        createHorario(usuario, diciplina, 19, 35, Calendar.TUESDAY, "BLOCO A - SALA 29");

        createHorario(usuario, diciplina, 20, 35, Calendar.TUESDAY, "BLOCO A - SALA 29");

        createHorario(usuario, diciplina, 21, 25, Calendar.TUESDAY, "BLOCO A - SALA 29");

        createNota(usuario, diciplina, 1, 7.0, 2);

        //SISTEMAS DIGITAIS
        diciplina = new Diciplina(3l);
        diciplina.setNome("SISTEMAS DIGITAIS DE CONTROLE");
        diciplinaDao.create(diciplina);

        createHorario(usuario, diciplina, 15, 10, Calendar.WEDNESDAY, "BLOCO A - SALA 32");

        createHorario(usuario, diciplina, 16, 00, Calendar.WEDNESDAY, "BLOCO A - SALA 32");

        createHorario(usuario, diciplina, 16, 50, Calendar.WEDNESDAY, "BLOCO A - SALA 32");

        createHorario(usuario, diciplina, 17, 40, Calendar.WEDNESDAY, "BLOCO A - SALA 32");

        createNota(usuario, diciplina, 1, 6.9, 4);

        //TOPICOS ESPECIAIS
        diciplina = new Diciplina(5l);
        diciplina.setNome("TÓPICOS ESPECIAIS EM COMPUTAÇÃO");
        diciplinaDao.create(diciplina);

        createHorario(usuario, diciplina, 18, 45, Calendar.WEDNESDAY, "BLOCO A - SALA 36");

        createHorario(usuario, diciplina, 19, 35, Calendar.WEDNESDAY, "BLOCO A - SALA 36");

        createHorario(usuario, diciplina, 20, 35, Calendar.WEDNESDAY, "BLOCO A - SALA 36");

        createHorario(usuario, diciplina, 21, 25, Calendar.WEDNESDAY, "BLOCO A - SALA 36");

        createNota(usuario, diciplina, 1, 7.0, 8);

        diciplina = new Diciplina(4l);
        diciplina.setNome("PROJETO DE REDES");
        diciplinaDao.create(diciplina);

        createHorario(usuario, diciplina, 18, 45, Calendar.THURSDAY, "BLOCO A - SALA 35");

        createHorario(usuario, diciplina, 19, 35, Calendar.THURSDAY, "BLOCO A - SALA 35");

        createHorario(usuario, diciplina, 20, 35, Calendar.THURSDAY, "BLOCO A - SALA 35");

        createHorario(usuario, diciplina, 21, 25, Calendar.THURSDAY, "BLOCO A - SALA 35");

        createNota(usuario, diciplina, 1, 7.2, 0);

    }

    private void createNota(Usuario usuario, Diciplina diciplina, int unidade, double dNota, int faltas) throws SQLException {
        getNotasDao();
        Nota nota = new Nota(usuario, diciplina, unidade, dNota, faltas);
        notasDao.create(nota);
    }

    private void createHorario(Usuario usuario, Diciplina diciplina, int hora, int minutos, int diaSemana, String local) throws SQLException {
        Dao<Horario, Long> horarioDao = getDao(Horario.class);

        Horario horario = new Horario();
        horario.setDiaSemana(diaSemana);
        horario.setDiciplina(diciplina);
        horario.setHora(hora);
        horario.setMinutos(minutos);
        horario.setUsuario(usuario);
        horario.setLocal(local);
        horarioDao.create(horario);
    }


    public Dao<Horario, Long> getHorarioDao() throws SQLException {
        if (horarioDao == null) {
            horarioDao = getDao(Horario.class);
        }
        return horarioDao;
    }

    public Dao<Diciplina, Long> getDiciplinaDao() throws SQLException {
        if (diciplinaDao == null) {
            diciplinaDao = getDao(Diciplina.class);
        }
        return diciplinaDao;
    }


    public Dao<Usuario, Long> getUsuarioDAO() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Nota, Long> getNotasDao() throws SQLException {
        if (notasDao == null) {
            notasDao = getDao(Nota.class);
        }
        return notasDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        usuarioDao = null;
        diciplinaDao = null;
        horarioDao = null;
        notasDao = null;
    }
}
