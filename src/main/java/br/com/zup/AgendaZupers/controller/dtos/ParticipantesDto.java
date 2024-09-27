package br.com.zup.AgendaZupers.controller.dtos;

public class ParticipantesDto {
    private String nome;
    private String email;
    private int id;

    public ParticipantesDto(String nome, String email) {}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}


}