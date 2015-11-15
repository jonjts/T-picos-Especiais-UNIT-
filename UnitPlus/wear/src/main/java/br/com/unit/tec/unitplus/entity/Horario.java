package br.com.unit.tec.unitplus.entity;


/**
 * Created by jon_j on 01/11/2015.
 */
public class Horario {

    private Long id;

    private Usuario usuario;

    private Diciplina diciplina;

    private Integer hora;

    private Integer minutos;

    private Integer diaSemana;

    private String local;

    public Horario() {
    }

    public Horario(Usuario usuario, Diciplina diciplina, Integer hora, Integer minutos, Integer diaSemana, String local) {
        this.usuario = usuario;
        this.diciplina = diciplina;
        this.hora = hora;
        this.minutos = minutos;
        this.diaSemana = diaSemana;
        this.local = local;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
