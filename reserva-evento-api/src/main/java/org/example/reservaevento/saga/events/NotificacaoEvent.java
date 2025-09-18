package org.example.reservaevento.saga.events;

public record NotificacaoEvent(
        String reservaId,
        String email,
        String mensagem
) {}
