package br.com.unit.tec.unitplus.entity;


/**
 * Created by jon_j on 01/11/2015.
 */
public class Diciplina {

    private Long id;

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
