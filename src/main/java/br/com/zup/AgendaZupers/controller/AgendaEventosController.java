package br.com.zup.AgendaZupers.controller;

import br.com.zup.AgendaZupers.controller.dtos.CadastroDto;
import br.com.zup.AgendaZupers.controller.dtos.ParticipantesDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/AgendaEventos")
public class AgendaEventosController {
    private List<CadastroDto> eventos = new ArrayList<>();
    private List<ParticipantesDto> participantes = new ArrayList<>();
    private int idController = 0;

    @PostMapping("/participantes")
    public String cadastrarParticipante(@RequestBody ParticipantesDto participanteDto) {
        if (participantes.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(participanteDto.getNome())
                || p.getEmail().equalsIgnoreCase(participanteDto.getEmail()))) {
            return "Erro: Nome ou email já cadastrado!";
        }

        participanteDto.setId(++idController);
        participantes.add(participanteDto);
        return "Participante cadastrado!";
    }

    @PostMapping
    public String cadastrarEventos(@RequestBody CadastroDto cadastroDto) {
        try {
            if (verificarParticipantes(cadastroDto.getParticipantes())) {
                cadastroDto.setId(++idController);
                eventos.add(cadastroDto);
                return "Evento cadastrado!";
            } else {
                return "Erro: Um ou mais participantes não estão cadastrados.";
            }
        } catch (Exception e) {
            return "Erro ao cadastrar o evento: " + e.getMessage();
        }
    }

    private boolean verificarParticipantes(String participantesInput) {
        if (participantesInput == null || participantesInput.isEmpty()) {
            return false;
        }
        String[] nomesParticipantes = participantesInput.split(",\\s*");
        for (String nome : nomesParticipantes) {
            boolean existe = participantes.stream()
                    .anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
            if (!existe) {
                return false;
            }
        }
        return true;
    }

    @GetMapping
    public List<CadastroDto> listarEventos(@RequestParam(value = "dias", required = false) Integer dias) {
        LocalDateTime agora = LocalDateTime.now();

        if (dias != null) {
            LocalDateTime limite = agora.plusDays(dias);
            return eventos.stream()
                    .filter(e -> e.getDataHora().isBefore(limite))
                    .toList();
        }

        return eventos.stream()
                .filter(e -> e.getDataHora().isAfter(agora))
                .toList();
    }

    @GetMapping("/buscar/{titulo}")
    public String buscarEventos(@PathVariable String titulo) {
        return eventos.stream()
                .filter(e -> e.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .map(e -> "Evento encontrado: " + e.getTitulo())
                .orElse("Nenhum evento cadastrado com o título: " + titulo);
    }

    @DeleteMapping("/deletar/{titulo}")
    public String deletarEventos(@PathVariable String titulo) {
        boolean removed = eventos.removeIf(e -> e.getTitulo().equalsIgnoreCase(titulo));
        if (removed) {
            return "Evento removido com sucesso!";
        } else {
            return "Evento não cadastrado com o título: " + titulo;
        }
    }

    @PostMapping("/cancelar/{titulo}")
    public String cancelarEvento(@PathVariable String titulo) {
        return eventos.stream()
                .filter(e -> e.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .map(e -> {
                    e.setStatus("CANCELADO");
                    return "Evento cancelado com sucesso!";
                })
                .orElse("Nenhum evento cadastrado com o título: " + titulo);
    }
}