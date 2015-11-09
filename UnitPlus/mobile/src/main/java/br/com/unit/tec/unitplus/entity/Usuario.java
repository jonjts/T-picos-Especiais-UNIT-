package br.com.unit.tec.unitplus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jon_j on 28/10/2015.
 */
@DatabaseTable(tableName = "usuario")
public class Usuario {

    @DatabaseField(canBeNull = false)
    private String nome;
    @DatabaseField(canBeNull = false)
    private String senha;
    @DatabaseField(canBeNull = false, id = true)
    private Long login;

    public Usuario() {
    }

    public Usuario(Long login) {
        this.login = login;
    }

    public Usuario(String senha, Long login) {
        this.senha = senha;
        this.login = login;
    }

    public Usuario(String nome, String senha, Long login) {
        this.nome = nome;
        this.senha = senha;
        this.login = login;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }
}
