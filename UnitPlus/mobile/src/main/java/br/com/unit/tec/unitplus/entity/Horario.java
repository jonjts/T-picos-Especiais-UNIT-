package br.com.unit.tec.unitplus.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;


/**
 * Created by jon_j on 01/11/2015.
 */
@DatabaseTable(tableName = "horario")
public class Horario {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = "id_usuario", foreign = true, foreignAutoRefresh = true)
    private Usuario usuario;

    @DatabaseField(columnName = "id_diciplina", foreign = true, foreignAutoRefresh = true)
    private Diciplina diciplina;

    @DatabaseField(columnName = "hora", canBeNull = false)
    private Integer hora;

    @DatabaseField(columnName = "minutos", canBeNull = false)
    private Integer minutos;

    @DatabaseField(columnName = "dia_semana", canBeNull = false)
    private Integer diaSemana;

    @DatabaseField
    private String local;

    public Horario() {
    }

    public Horario(Long id) {
        this.id = id;
    }

    public Horario(Long id, Usuario usuario, Diciplina diciplina, Integer hora, Integer minutos, Integer diaSemana, String local) {
        this.id = id;
        this.usuario = usuario;
        this.diciplina = diciplina;
        this.hora = hora;
        this.minutos = minutos;
        this.diaSemana = diaSemana;
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Diciplina getDiciplina() {
        return diciplina;
    }

    public void setDiciplina(Diciplina diciplina) {
        this.diciplina = diciplina;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }
}
