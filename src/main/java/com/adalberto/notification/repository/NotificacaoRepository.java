package com.adalberto.notification.repository;

import com.adalberto.notification.domain.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findyByStatus(String status);
    List<Notificacao> findByDestinatario(String destinatario);
}
