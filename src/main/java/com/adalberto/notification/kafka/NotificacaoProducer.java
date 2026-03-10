package com.adalberto.notification.kafka;

import com.adalberto.notification.dto.NotificacaoRequest;
import com.adalberto.notification.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacaoProducer {

    private static final String TOPIC = "notificacao-criada";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publicar(NotificacaoRequest request) {
        try {
            String payload = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(TOPIC, payload);
            log.info("Notificação publicada no tópico {}: {}", TOPIC, payload);
        } catch (Exception e) {
            log.error("Erro ao publicar notificação: {}", e);
            throw new BusinessException("Erro ao publicar notificação");

        }
    }
}
