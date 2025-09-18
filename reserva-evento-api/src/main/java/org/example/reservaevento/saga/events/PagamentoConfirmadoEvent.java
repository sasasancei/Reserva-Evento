package org.example.reservaevento.saga.events;

public record PagamentoConfirmadoEvent(
        String reservaId
) {}
