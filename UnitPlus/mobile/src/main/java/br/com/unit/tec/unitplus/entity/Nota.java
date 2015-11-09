package br.com.unit.tec.unitplus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jon_j on 02/11/2015.
 */
@DatabaseTable(tableName = "nota")
public class Nota {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = "id_usuario", foreign = true, foreignAutoRefresh = true)
    private Usuario usuario;

    @DatabaseField(columnName = "id_diciplina", foreign = true, foreignAutoRefresh = true)
    private Diciplina diciplina;

    @DatabaseField
    private Integer unidade;

    @DatabaseField
    private Double nota;

    @DatabaseField
    private Integer faltas;

    public Nota() {
    }

    public Nota(Long id) {
        this.id = id;
    }

    public Nota(Usuario usuario, Diciplina diciplina, Integer unidade, Double nota, Integer faltas) {
        this.usuario = usuario;
        this.diciplina = diciplina;
        this.unidade = unidade;
        this.nota = nota;
        this.faltas = faltas;
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

    public Integer getUnidade() {
        return unidade;
    }

    public void setUnidade(Integer unidade) {
        this.unidade = unidade;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }
}
