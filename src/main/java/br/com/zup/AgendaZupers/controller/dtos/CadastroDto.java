package br.com.zup.AgendaZupers.controller.dtos;

import java.time.LocalDateTime;

public class CadastroDto {
    private String titulo;
    private LocalDateTime dataHora;
    private String descricao;
    private String localizacao;
    private String participantes;
    private int id;
    private String status;

    //PADRAO BEN
    public CadastroDto() {}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public LocalDateTime getDataHora() {return dataHora;}

    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getLocalizacao() {return localizacao;}

    public void setLocalizacao(String localizacao) {this.localizacao = localizacao;}

    public String getParticipantes() {return participantes;}

    public void setParticipantes(String participantes) {this.participantes = participantes;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

}