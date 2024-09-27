package br.com.zup.AgendaZupers.controller.dtos;

public class EventoDto {
    private String nomeEvento;
    private String descricao;

    // Construtor padrão
    public EventoDto() {}

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}