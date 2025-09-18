package org.example.reservaevento.saga.events;

public record PagamentoFalhadoEvent(
        String reservaId,
        String motivo
) {}

