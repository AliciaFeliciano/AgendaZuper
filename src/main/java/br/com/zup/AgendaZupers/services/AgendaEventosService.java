package br.com.zup.AgendaZupers.services;

import br.com.zup.AgendaZupers.controllers.dtos.CadastroDto;
import br.com.zup.AgendaZupers.controllers.dtos.EventoDto;
import br.com.zup.AgendaZupers.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendaEventosService {

    private List<CadastroDto> eventos = new ArrayList<>();

    public String cadastrarEvento(CadastroDto cadastroDto) {
        validarCamposObrigatorios(cadastroDto);
        String id = UUID.randomUUID().toString();
        cadastroDto.getEvento().setId(id);
        cadastroDto.getEvento().setEventoAtivo(true);
        eventos.add(cadastroDto);
        return "Evento cadastrado com sucesso!";
    }

    public Map<String, Object> listarEventos(Integer dias) {
        Date hoje = new Date();
        Map<String, List<EventoDto>> eventosPorData = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoje);

        if (dias >= 0) {
            cal.add(Calendar.DAY_OF_MONTH, dias);
            Date dataLimite = cal.getTime();
            for (CadastroDto cadastroDto : eventos) {
                if (!cadastroDto.getDataInicio().before(hoje) && !cadastroDto.getDataInicio().after(dataLimite)) {
                    String dataKey = cadastroDto.getDataInicio().toString();
                    EventoDto eventoDto = cadastroDto.getEvento();
                    eventoDto.setEventoAtivo(true);
                    eventosPorData.putIfAbsent(dataKey, new ArrayList<>());
                    eventosPorData.get(dataKey).add(eventoDto);
                }
            }
        } else {
            cal.add(Calendar.DAY_OF_MONTH, dias);
            Date dataLimite = cal.getTime();
            for (CadastroDto cadastroDto : eventos) {
                if (cadastroDto.getDataInicio().before(hoje) && !cadastroDto.getDataInicio().before(dataLimite)) {
                    String dataKey = cadastroDto.getDataInicio().toString();
                    EventoDto eventoDto = cadastroDto.getEvento();
                    eventoDto.setEventoAtivo(false);
                    eventosPorData.putIfAbsent(dataKey, new ArrayList<>());
                    eventosPorData.get(dataKey).add(eventoDto);
                }
            }
        }

        Map<String, Object> resposta = new HashMap<>();
        if (!eventosPorData.isEmpty()) {
            resposta.put("eventos", eventosPorData);
        } else {
            resposta.put("message", "Nenhum evento encontrado.");
        }
        return resposta;
    }

    public List<CadastroDto> listarTodosEventos() {
        return new ArrayList<>(eventos);
    }

    public String cancelarEvento(String id) {
        return eventos.stream()
                .filter(e -> e.getEvento().getId().equals(id))
                .findFirst()
                .map(e -> {
                    e.getEvento().setEventoAtivo(false);
                    return "Evento cancelado com sucesso!";
                })
                .orElse("Evento não encontrado");
    }

    public void deletarEvento(String id) {
        boolean removed = eventos.removeIf(e -> e.getEvento().getId().equals(id));
        if (!removed) {
            throw new ValidationException("Evento não encontrado");
        }
    }

    public List<CadastroDto> buscarEventoPorNome(String nomeEvento) {
        return eventos.stream()
                .filter(e -> e.getEvento().getNomeEvento().equalsIgnoreCase(nomeEvento))
                .collect(Collectors.toList());
    }

    private void validarCamposObrigatorios(CadastroDto cadastroDto) {
        List<Map<String, String>> erros = new ArrayList<>();
        if (cadastroDto.getDataInicio() == null) {
            erros.add(Map.of("campo", "dataInicio", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getDataFim() == null) {
            erros.add(Map.of("campo", "dataFim", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getHoraInicio() == null) {
            erros.add(Map.of("campo", "horaInicio", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getHoraFim() == null) {
            erros.add(Map.of("campo", "horaFim", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getEvento() == null || cadastroDto.getEvento().getNomeEvento() == null) {
            erros.add(Map.of("campo", "nomeEvento", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getEvento() == null || cadastroDto.getEvento().getDescricao() == null) {
            erros.add(Map.of("campo", "descricao", "mensagem", "É obrigatório"));
        }
        if (cadastroDto.getDataInicio() != null && cadastroDto.getDataFim() != null && cadastroDto.getDataInicio().after(cadastroDto.getDataFim())) {
            erros.add(Map.of("campo", "dataInicio", "mensagem", "Deve ser anterior a dataFim"));
        }
        if (cadastroDto.getHoraInicio() != null && cadastroDto.getHoraFim() != null && cadastroDto.getHoraInicio().after(cadastroDto.getHoraFim())) {
            erros.add(Map.of("campo", "horaInicio", "mensagem", "Deve ser anterior a horaFim"));
        }
        if (!erros.isEmpty()) {
            throw new ValidationException(erros.toString());
        }
    }
}