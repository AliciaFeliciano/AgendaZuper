package br.com.zup.AgendaZupers.controllers;

import br.com.zup.AgendaZupers.exceptions.ValidationException;
import br.com.zup.AgendaZupers.services.AgendaEventosService;
import br.com.zup.AgendaZupers.controllers.dtos.CadastroDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AgendaEvento")
public class AgendaEventosController {

    private final AgendaEventosService agendaEventosService;

    public AgendaEventosController(AgendaEventosService agendaEventosService) {
        this.agendaEventosService = agendaEventosService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEvento(@RequestBody CadastroDto cadastroDto) {
        try {
            String message = agendaEventosService.cadastrarEvento(cadastroDto);
            return ResponseEntity.ok(message);
        } catch (ValidationException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarEventos(@RequestParam(value = "dias", required = false) Integer dias) {
        if (dias == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "O parâmetro 'dias' é obrigatório."));
        }
        Map<String, Object> eventos = agendaEventosService.listarEventos(dias);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CadastroDto>> listarTodosEventos() {
        List<CadastroDto> eventos = agendaEventosService.listarTodosEventos();
        if (eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CadastroDto>> buscarEventoPorNome(@RequestParam("nome") String nomeEvento) {
        List<CadastroDto> eventos = agendaEventosService.buscarEventoPorNome(nomeEvento);
        if (eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarEvento(@PathVariable String id) {
        String message = agendaEventosService.cancelarEvento(id);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarEvento(@PathVariable String id) {
        try {
            agendaEventosService.deletarEvento(id);
            return ResponseEntity.ok("Evento deletado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}