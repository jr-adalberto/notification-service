package com.adalberto.notification.dto;

import com.adalberto.notification.domain.enums.TipoNotificacao;
import com.adalberto.notification.domain.model.Notificacao;
import java.time.LocalDateTime;

public record NotificacaoResponse(
        Long id,
        String destinatario,
        String mensagem,
        TipoNotificacao tipo,
        String status,
        LocalDateTime criadoEm
) {
    public static NotificacaoResponse from(Notificacao n) {
        return new NotificacaoResponse(
                n.getId(),
                n.getDestinatario(),
                n.getMensagem(),
                n.getTipoNotificacao(),
                n.getStatus(),
                n.getCreatedAt()
        );
    }
}