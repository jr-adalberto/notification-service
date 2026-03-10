package com.adalberto.notification.kafka;

import com.adalberto.notification.domain.model.Notificacao;
import com.adalberto.notification.dto.NotificacaoRequest;
import com.adalberto.notification.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@CommonsLog
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificacaoRepository repository;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "notificacao-criada", groupId = "notification-group")
    public void consumir(String payload) {
        try {
            NotificacaoRequest request = mapper.readValue(payload, NotificacaoRequest.class);

            Notificacao notificacao = Notificacao.builder()
                    .destinatario(request.destinatario())
                    .mensagem(request.mensagem())
                    .tipoNotificacao(request.tipo())
                    .build();

            notificacao.setStatus("PROCESSADO");
            repository.save(notificacao);

            log.info("Notificação processada e salva: {}", request.destinatario());
        } catch (Exception e) {
            log.error("Erro ao processar evento Kafka: {}", payload, e);
        }
    }
}
