package com.adalberto.notification.controller;

import com.adalberto.notification.dto.NotificacaoRequest;
import com.adalberto.notification.dto.NotificacaoResponse;
import com.adalberto.notification.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Gerenciamento de notificações assincronas")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Enviar notificacao", description = "Publica evento no Kafka para envio de notificacao assincrona")
    public void enviar(@RequestBody @Valid NotificacaoRequest request) {
        notificacaoService.enviar(request);
    }

    @GetMapping
    @Operation(summary = "Listar notificacoes", description = "Retorna todas as notificações enviadas")
    public List<NotificacaoResponse> listar() {
        return notificacaoService.listarNotificacoes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter notificacao por ID", description = "Retorna os detalhes de uma notificação específica pelo seu ID")
    public NotificacaoResponse buscarPorId(@PathVariable Long id) {
        return notificacaoService.buscarPorId(id);
    }
}
