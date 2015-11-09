package br.com.unit.tec.unitplus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jon_j on 01/11/2015.
 */
@DatabaseTable(tableName = "diciplina")
public class Diciplina {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String nome;


    public Diciplina() {
    }

    public Diciplina(Long id) {
        this.id = id;
    }

    public Diciplina(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
