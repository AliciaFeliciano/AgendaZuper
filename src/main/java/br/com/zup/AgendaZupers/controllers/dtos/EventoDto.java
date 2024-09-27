package br.com.zup.AgendaZupers.controllers.dtos;

public class EventoDto {
    private String id;
    private String nomeEvento;
    private String descricao;
    private boolean eventoAtivo;

    // padaro ben

    public EventoDto() {}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNomeEvento() {return nomeEvento;}

    public void setNomeEvento(String nomeEvento) {this.nomeEvento = nomeEvento;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public boolean isEventoAtivo() {return eventoAtivo;}

    public void setEventoAtivo(boolean eventoAtivo) {this.eventoAtivo = eventoAtivo;}
}
