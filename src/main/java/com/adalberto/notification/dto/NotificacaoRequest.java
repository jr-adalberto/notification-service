package com.adalberto.notification.dto;

import com.adalberto.notification.domain.enums.TipoNotificacao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacaoRequest(

        @NotBlank(message = "Destinatário é obrigatório")
        @Email(message = "Destinatário deve ser um email válido")
        String destinatario,

        @NotBlank(message = "Mensagem é obrigatória")
        @Size(max = 500, message = "Mensagem deve conter no máximo 500 caracteres")
        String mensagem,

        @NotNull(message = "Tipo de notificação é obrigatório")
        TipoNotificacao tipo

) {}