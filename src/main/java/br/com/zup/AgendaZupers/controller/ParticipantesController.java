package br.com.zup.AgendaZupers.controller;

import br.com.zup.AgendaZupers.controller.dtos.ParticipantesDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Participantes")
public class ParticipantesController {
    private List<ParticipantesDto> participantes = new ArrayList<>();
    private int idController = 0;

    @PostMapping
    public String cadastrarParticipante(@RequestBody ParticipantesDto participanteDto) {
        // Verifica se o nome ou email já estão cadastrados
        if (participantes.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(participanteDto.getNome())
                || p.getEmail().equalsIgnoreCase(participanteDto.getEmail()))) {
            return "Erro: Nome ou email já cadastrado!";
        }

        participanteDto.setId(++idController);
        participantes.add(participanteDto);
        return "Participante cadastrado!";
    }

    @GetMapping("/{nome}")
    public String buscarParticipante(@PathVariable String nome) {
        return participantes.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .map(p -> "Participante encontrado: " + p.getNome())
                .orElse("Nenhum participante cadastrado.");
    }

    @DeleteMapping("/deletar")
    public String deletarParticipante(@RequestBody String nome) {
        boolean deletado = participantes.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
        if (deletado) {
            return "Participante removido!";
        } else {
            return "Participante não cadastrado!";
        }
    }
}