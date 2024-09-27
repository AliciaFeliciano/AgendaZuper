package br.com.zup.AgendaZupers.controller;

import br.com.zup.AgendaZupers.controller.dtos.CadastroDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;


@RestController
@RequestMapping("/AgendaEventos")
public class AgendaEventosController {
    private List<CadastroDto> eventos = new ArrayList<>();

    @PostMapping
    public String cadastrarEventos(@RequestBody CadastroDto cadastroDto) {
        try {
            eventos.add(cadastroDto);
            return "Evento cadastrado!";
        } catch (Exception e) {
            return "Erro ao cadastrar o evento: " + e.getMessage();
        }
    }

    @GetMapping
    public List<CadastroDto> listarEventos() {
        return eventos;
    }

    @GetMapping("/buscar/{nomeEvento}")
    public String buscarEventos(@PathVariable String nomeEvento) {
        return eventos.stream()
                .filter(e -> e.getEvento() != null && e.getEvento().getNomeEvento().equalsIgnoreCase(nomeEvento))
                .findFirst()
                .map(e -> "Evento encontrado: " + e.getEvento().getNomeEvento())
                .orElse("Nenhum evento cadastrado");
    }

    @DeleteMapping("/deletar/{nomeEvento}")
    public String deletarEventos(@PathVariable String nomeEvento) {
        boolean removed = eventos.removeIf(e -> e.getEvento() != null && e.getEvento().getNomeEvento().equalsIgnoreCase(nomeEvento));
        return removed ? "Evento removido com sucesso!" : "Evento não cadastrado";
    }

    @PostMapping("/cancelar/{nomeEvento}")
    public String cancelarEvento(@PathVariable String nomeEvento) {
        return eventos.stream()
                .filter(e -> e.getEvento() != null && e.getEvento().getNomeEvento().equalsIgnoreCase(nomeEvento))
                .findFirst()
                .map(e -> {
                    e.getEvento().setDescricao("CANCELADO");
                    return "Evento cancelado com sucesso!";
                })
                .orElse("Nenhum evento cadastrado");
    }

    @GetMapping("/eventos")
    public List<CadastroDto> buscarEventosPorDias(@RequestParam("dias") int dias) {
        Date hoje = new Date();
        long diasEmMilissegundos = dias * 24 * 60 * 60 * 1000L;
        Date dataLimite = new Date(hoje.getTime() + diasEmMilissegundos);

        return eventos.stream()
                .filter(evento -> {
                    Date dataInicial = evento.getDataInicio();
                    return (dias > 0) ? (dataInicial.after(hoje) && dataInicial.before(dataLimite)) :
                            (dataInicial.before(hoje) && dataInicial.after(new Date(hoje.getTime() - (-diasEmMilissegundos))));
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/eventosPorDia")
    public Object buscarEventosDentroDeUmDia() {
        Date hoje = new Date();
        Date dataLimite = new Date(hoje.getTime() + 24 * 60 * 60 * 1000L);

        System.out.println("Buscando eventos entre: " + hoje + " e " + dataLimite);

        List<Object> eventosDentroDeUmDia = eventos.stream()
                .filter(evento -> {
                    Date dataInicio = evento.getDataInicio();
                    System.out.println("Verificando evento: " + evento.getEvento().getNomeEvento() + " com data " + dataInicio);
                    return dataInicio.after(hoje) && dataInicio.before(dataLimite);
                })
                .map(evento -> {
                    return Map.of(
                            "id", evento.getEvento().getNomeEvento(),
                            "nomeEvento", evento.getEvento().getNomeEvento(),
                            "descricao", evento.getEvento().getDescricao(),
                            "horaInicio", evento.getHoraInicio(),
                            "eventoAtivo", true
                    );
                })
                .collect(Collectors.toList());

        return Map.of(
                "dataInicio", hoje,
                "eventos", eventosDentroDeUmDia
        );
    }

}
