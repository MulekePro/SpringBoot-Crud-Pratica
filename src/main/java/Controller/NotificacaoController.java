package Controller;

import Entidades.DTO.NotificacaoRequestDTO;
import Entidades.normal.Notificacao;
import Servicos.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Notificacao> criar(@RequestBody @Valid NotificacaoRequestDTO request) {
        Notificacao novaNotificacao = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNotificacao);
    }


    @GetMapping
    public ResponseEntity<List<Notificacao>> listar(
            @RequestParam(name = "agravo", required = false) String agravo,
            @RequestParam(name = "nomePaciente", required = false) String nomePaciente,
            @RequestParam(name = "dataNotificacaoDe", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNotificacaoDe,
            @RequestParam(name = "dataNotificacaoAte", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNotificacaoAte,
            @RequestParam(name = "duplicadas", required = false) Boolean duplicadas) {
        List<Notificacao> lista = service.listar(agravo, nomePaciente, dataNotificacaoDe, dataNotificacaoAte, duplicadas);
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        Notificacao notificacao = service.buscarPorId(id);
        return ResponseEntity.ok(notificacao);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> atualizar(@PathVariable Long id, @RequestBody @Valid NotificacaoRequestDTO request) {
        Notificacao notificacaoAtualizada = service.atualizar(id, request);
        return ResponseEntity.ok(notificacaoAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
