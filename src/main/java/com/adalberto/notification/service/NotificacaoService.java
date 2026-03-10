package com.adalberto.notification.service;

import com.adalberto.notification.dto.NotificacaoRequest;
import com.adalberto.notification.dto.NotificacaoResponse;
import com.adalberto.notification.exception.BusinessException;
import com.adalberto.notification.kafka.NotificacaoProducer;
import com.adalberto.notification.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoProducer notificacaoProducer;
    private final NotificacaoRepository notificacaoRepository;

    public void enviar(NotificacaoRequest notificacaoRequest) {
        notificacaoProducer.publicar(
                notificacaoRequest
        );
    }

    public List<NotificacaoResponse> listarNotificacoes() {
        return notificacaoRepository.findAll()
                .stream()
                .map(NotificacaoResponse::from)
                .toList();
    }

    public NotificacaoResponse buscarPorId(Long id) {
        return notificacaoRepository.findById(id)
                .map(NotificacaoResponse::from)
                .orElseThrow(() -> new BusinessException("Notificação não encontrada" + id));
    }
}
