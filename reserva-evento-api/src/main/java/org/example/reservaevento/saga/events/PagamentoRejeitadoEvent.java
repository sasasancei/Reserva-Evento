package org.example.reservaevento.saga.events;

/**
 * Evento de domínio que representa a rejeição de um pagamento.
 * Este evento é emitido pelo serviço de pagamento após a falha da transação.
 */
public record PagamentoRejeitadoEvent(
        String reservaId,
        String motivo) {}
