package com.adalberto.notification.service;

import com.adalberto.notification.domain.enums.TipoNotificacao;
import com.adalberto.notification.dto.NotificacaoRequest;
import com.adalberto.notification.exception.BusinessException;
import com.adalberto.notification.kafka.NotificacaoProducer;
import com.adalberto.notification.repository.NotificacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @InjectMocks
    private NotificacaoService notificacaoService;
    @Mock
    private NotificacaoProducer notificacaoProducer;
    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Test
    void deveChamarProducerAoEnviarNotificacao() {
        var request = new NotificacaoRequest("destinario@mail.com", "Mensagem de teste",
                TipoNotificacao.EMAIL);
        notificacaoService.enviar(request);
        verify(notificacaoProducer).publicar(request);
    }

    @Test
    void deveLancarExcecaoAoNotificacaoNaoEncontrada() {
        when(notificacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> notificacaoService.buscarPorId(1L));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaNotificacoes(){
        when(notificacaoRepository.findAll()).thenReturn(Collections.emptyList());
        var resultado = notificacaoService.listarNotificacoes();
        assertTrue(resultado.isEmpty());
    }
}