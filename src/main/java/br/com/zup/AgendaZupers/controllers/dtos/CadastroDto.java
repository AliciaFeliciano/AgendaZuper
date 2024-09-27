package br.com.zup.AgendaZupers.controllers.dtos;

import java.sql.Time;
import java.util.Date;

public class CadastroDto {
    private Date dataInicio;
    private Date dataFim;
    private Time horaInicio;
    private Time horaFim;
    private EventoDto evento;

    // PADRAO BEN
    public CadastroDto() {}

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }

    public EventoDto getEvento() {
        return evento;
    }

    public void setEvento(EventoDto evento) {
        this.evento = evento;
    }
}